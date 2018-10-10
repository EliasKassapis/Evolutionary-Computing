function InvokeFunction
{
    Param ([int]$populationSize, [int]$fittestSize, [int]$recombinationSize, [int]$mutationSize, [string]$function)

    $maxScore = 0
    $i = 0
    while ($i -lt 10)
    {
        $command = '& java -DpopulationSize=' + $populationSize + ' -DfittestSize=' + $fittestSize + ' -DrecombinationSize=' + $recombinationSize + ' -DmutationSize=' + $mutationSize + ' -jar testrun.jar -submission=player65 -evaluation=' + $function + ' -seed=1 2>&1'
        $output = Invoke-Expression $command
        $splittedOutput = $output.split(' ')
        $currentScore = [System.Decimal]($splittedOutput[$splittedOutput.Length - 3])
        if ($maxScore -lt $currentScore)
        {
            $maxScore = $currentScore
        }
        $i++
    }

    return $maxScore
}

function TuneFunction
{
    Param ([string]$function)
    $maxScore = 0
    $populationSize = 100
    $bestPopulationSize = 0
    $bestFittestSize = 0
    $bestRecombinationSize = 0
    $bestMutationSize = 0
    
    For ($i = 0; $i -lt $populationSize; $i+=2)
    {
        For ($j = 0; $j -le $populationSize - $i; $j+=2)
        {
            For ($k = 0; $k -le $populationSize - $i - $j; $k+=2)
            {
                Write-Host "invoking with fittest - " $i "; recombination - " $j "; mutation - " $k
                $currentScore = InvokeFunction -populationSize $populationSize -fittestSize $i -recombinationSize $j -mutationSize $k -function $function
                if ($maxScore -lt $currentScore)
                {
                    $maxScore = $currentScore
                    $bestPopulationSize = $populationSize
                    $bestFittestSize = $i
                    $bestRecombinationSize = $j
                    $bestMutationSize = $k
                }
            }
        }
    }

    Write-Host $function " best result:"
    Write-Host " - Score - " $maxScore
    Write-Host " - Population size - " $bestPopulationSize
    Write-Host " - Fittest size - " $bestFittestSize
    Write-Host " - Recombination size - " $bestRecombinationSize
    Write-Host " - Mutation size - " $bestMutationSize
    return $maxScore, $bestPopulationSize, $bestFittestSize, $bestRecombinationSize, $bestMutationSize
}

Write-Host "Starting parameter tuning:"

Write-Host "Compiling..."
$compilationOutput = & javac -Werror -cp .\contest.jar player65.java *.java -Xlint:unchecked 2>&1
if (!([string]::IsNullOrEmpty($compilationOutput)))
{
    Write-Host $compilationOutput
    Write-Host "Error occured while compiling. Aborting script..."
    return
}

jar cmf MainClass.txt submission.jar player65.class *.class
jar uf contest.jar player65.class *.class

TuneFunction -function SphereEvaluation

# TuneFunction -function BentCigarFunction
# TuneFunction -function KatsuuraEvaluation
# TuneFunction -function SchaffersEvaluation
# InvokeFunction -populationSize 100 -fittestSize 10 -recombinationSize 60 -mutationSize 30 -function SphereEvaluation
# InvokeFunction -populationSize 100 -fittestSize 10 -recombinationSize 60 -mutationSize 30 -function BentCigarFunction
# InvokeFunction -populationSize 100 -fittestSize 10 -recombinationSize 60 -mutationSize 30 -function KatsuuraEvaluation
# InvokeFunction -populationSize 100 -fittestSize 10 -recombinationSize 60 -mutationSize 30 -function SchaffersEvaluation