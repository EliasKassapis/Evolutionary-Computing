package Constants;

import Enums.*;

public class Constants{
    
    public final static int POPULATION_SIZE = 100;

    public final static int PARENTS_SIZE = 60;

    public final static int MUTATION_SIZE = 20;

    public final static int FITTEST_SIZE = POPULATION_SIZE - PARENTS_SIZE - MUTATION_SIZE;

    public final static int CYCLES_SIZE = 100;

    public final static MutationType CURRENT_MUTATION_TYPE = MutationType.BINARY;

    public final static ParentSelectionType CURRENT_PARENT_SELECTION_TYPE = ParentSelectionType.ROULETTE_WHEEL;
    
    public final static PhenotypeRepresentation CURRENT_PHENOTYPE_REPRESENTATION = PhenotypeRepresentation.DOUBLE;
    
    public final static GenotypeRepresentation CURRENT_GENOTYPE_REPRESENTATION = GenotypeRepresentation.BINARY;

    public final static int BINARY_REPRESENTATION_LENGTH = 5;

    public final static int MAX_PHENOTYPE_NUMBER_SIZE = 31;
}