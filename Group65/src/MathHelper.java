public class MathHelper {
    
    // Turns the input value x into a binary String of length "binaryLength"
    public static int[] makeBinary(int intNumber){
        if(intNumber > 31){
            //This should only be an issue when using Double Recombination.
            intNumber = 31;
            //System.out.println("Binary too large!");
        }
        //produced issues when binarylength != Constants.BINARY_REPRESENTATION_LENGTH
        //int binaryLength = getIntBinaryLength(intNumber);

        // Initialize the array with zeros only
        int[] output = new int[Constants.BINARY_REPRESENTATION_LENGTH];
        for (int i = 0; i < Constants.BINARY_REPRESENTATION_LENGTH; i++){
            output[i] = 0;
        }

        // Calculate the binary value
        for (int i = Constants.BINARY_REPRESENTATION_LENGTH - 1; i >= 0; i--){
            output[i] = intNumber % 2;
            intNumber = intNumber/2;
        }
        
        return output;
    }

    // Turns a binary number in int[] format into a decimal number as an Integer
    public static int makeDecimal(int[] intArray){
        int p = 0;
        for (int i = 0; i < intArray.length; i++){
            p += intArray[i] * Math.pow(2, intArray.length - i - 1);
        }

        return p;
    }

    // Prints a binary to the console for testing purposes
    public static String getBinaryString(int[] x){
        String result = "";
        for (int i : x) {
            result += i;
        }

        return result;
    }

    public static int getIntBinaryLength(int intNumber){
        int binaryLength = 1;
        intNumber = (int)(Math.floor((double)(intNumber/2)));

        while (intNumber != 0){
            binaryLength++;
            intNumber = (int)(Math.floor((double)(intNumber/2)));
        }

        return binaryLength;
    }
}