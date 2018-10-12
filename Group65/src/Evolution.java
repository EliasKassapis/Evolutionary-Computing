import org.vu.contest.ContestEvaluation;

/**
 * OPTIMIZATION FINDINGS:
 * Evaluating fitness takes ages, but can't be optimized ->  Not our code! [50-60%]
 * Recombination takes most of the remaining time [20-30%]
 * Mutation is relatively easy [5-10%]
 * The rest is negligible
 */

public class Evolution {
    public static ContestEvaluation eval;
    static void startEvolutionaryAlgorithm(ContestEvaluation evaluation, int eval_limit) {
        eval= evaluation;

        int populationSize = Constants.POPULATION_SIZE;
        int initialFittestSize = Constants.FITTEST_SIZE;
        int recombinationSize = Constants.RECOMBINATION_SIZE;
        int initialMutationSize = Constants.MUTATION_SIZE;

        String populationSizeString = System.getProperty("populationSize");
        if (populationSizeString != null && !populationSizeString.isEmpty()) {
            populationSize = Integer.parseInt(populationSizeString);
        }

        String fittestSizeString = System.getProperty("fittestSize");
        if (fittestSizeString != null && !fittestSizeString.isEmpty()) {
            initialFittestSize = Integer.parseInt(fittestSizeString);
        }

        String recombinationSizeString = System.getProperty("recombinationSize");
        if (recombinationSizeString != null && !recombinationSizeString.isEmpty()) {
            recombinationSize = Integer.parseInt(recombinationSizeString);
        }

        String mutationSizeString = System.getProperty("mutationSize");
        if (mutationSizeString != null && !mutationSizeString.isEmpty()) {
            initialMutationSize = Integer.parseInt(mutationSizeString);
        }
        
        Population tribe = new Population(
            Constants.CURRENT_PARENT_SELECTION_TYPE,
            populationSize);

        int cycles = eval_limit / populationSize;
        int last_cycles_without_mutation = cycles / 20; // 5 %
        int fittestSize = initialFittestSize;
        int mutationSize = initialMutationSize;
        for (int i = 0; i < cycles; i++) {
            // If we reach the last last_cycles_without_mutation cycles, 
            // we must stop mutating in order to preserve the currently found good population
            if (cycles - i < last_cycles_without_mutation) {
                fittestSize = initialFittestSize + mutationSize;
                mutationSize = 0;
            }
            tribe.recalculateFitness();

            Population nextGeneration = new Population(
                Constants.CURRENT_PARENT_SELECTION_TYPE,
                populationSize);
            nextGeneration.clearPopulation();

            // RECOMBINATION
            DoubleIndividual[] newChildren = tribe.createNewChildren(recombinationSize);
            nextGeneration.addIndividuals(newChildren);


            // MUTATION
            if (mutationSize > 0) {
                DoubleIndividual[] mutatedChildren = tribe.mutateIndividualsByDouble(mutationSize);
                nextGeneration.addIndividuals(mutatedChildren);
            }

            DoubleIndividual[] fittestIndividuals = tribe.getTopIndividuals(fittestSize);
            nextGeneration.addIndividuals(fittestIndividuals);
            tribe = nextGeneration;
        }
    }
}