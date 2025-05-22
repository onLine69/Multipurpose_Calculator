package model.programmerCalculator;
import java.math.*;

public class DecimalConversion {
    public DecimalConversion(){}

    public static String convertTo(String decimalForm, String newNumberSystem, char part){
        String convertedValue = "";

        /*
         * Checks first whether the argument is empty, then return an empty string.
         * When the argument is "0", returns "0".
         * And if the argument isn't one of those, convert the number to the desired sumber system. 
         */
        if (decimalForm.equals("")){
            convertedValue = "";
        } else {
            decimalForm = toDecimal(decimalForm, part);    //getting rid of the unnecessary 0s

            if (decimalForm.equals("0")){
                convertedValue = "0";
            } else {
                switch (newNumberSystem){
                    case "Binary":
                        convertedValue = toBinary(decimalForm, part);
                        break;
                    case "Octal":
                        convertedValue = toOctal(decimalForm, part);
                        break;
                    case "Decimal":
                        convertedValue = toDecimal(decimalForm, part);
                        break;
                    case "Hexadecimal":
                        convertedValue = toHexadecimal(decimalForm, part);
                        break;
                    default:
                        convertedValue = "Number System not Listed";
                        break;
                }
            }
        } 

        return convertedValue;
    }

    /**
     *  Convert the decimal number to its binary equivalent.
     */
    private static String toBinary(String decimalForm, char part){
        String finalBinary = "";    //the remainder for every division by 2
        
        if (part == 'w'){
            BigInteger originalDecimal = new BigInteger(decimalForm);    //to accommodate really large numbers
            BigInteger two = new BigInteger("2");   //need another BigInteger to perform operation to another BigInteger
            String qoutient = "";    //temporary placement for the quotient

            /*
                Step 1: Check whether the decimal number is not 0
                Step 2: If 0, return 0
                Step 3: If not 0, get the remainder when divided by 2. Place it to the finalBinary (from right to left)
                Step 4: Divide the decimal number by 2.
                Step 5: Repeat
            */
            while (!qoutient.equals("0")){
                finalBinary = originalDecimal.mod(two) + finalBinary;
                qoutient = String.valueOf(originalDecimal.divide(two));
                originalDecimal = new BigInteger(qoutient);
            }
        } else if (part == 'd'){
            decimalForm = "0." + decimalForm;   //making it decimal

            BigDecimal originalDecimalPart = new BigDecimal(decimalForm);   //to handle really large numbers
            BigDecimal one = new BigDecimal("1.0");     //to be subtracted and compared to
            BigDecimal two = new BigDecimal("2.0");     //to be multiplied
            String result = "";
            int count = 0;

            /*
                Step 1: Check whether the decimalForm is < 1
                Step 2: If > 1, add "1" to the string then subtract 1 from the decimalForm. If less than multiply by 2.
                Step 3: Step 1
                Step 4: Repeat Steps 1 to 3.
                Step 5: Stop the process when decimalForm == 0 or count > 100.
             */
            while (originalDecimalPart.compareTo(one) < 0 && count < 100){
                originalDecimalPart = originalDecimalPart.multiply(two);
                if (originalDecimalPart.compareTo(one) >= 0){
                    result += "1";
                    if (originalDecimalPart.compareTo(one) == 0){
                        count = 101;
                    }
                    originalDecimalPart = originalDecimalPart.subtract(one);
                } else {
                    result += "0";
                }
                count++;
            }

            if (count == 100){
                result += "...";    //to indicate that it is nonterminating 
            }

            finalBinary = result;
        }
        return finalBinary;
    }

    /**
     *  Convert the decimal number to its octal equivalent.
     */
    private static String toOctal(String decimalForm, char part){
        //transform the octal number to its binary equivalent
        String binaryForm = toBinary(decimalForm, part);

        //making the number of digits divisible by 3
        if (part == 'w'){
            //adding 0s in front
            while (binaryForm.length()%3 != 0){
                binaryForm = "0" + binaryForm;
            }
        } else if (part == 'd'){
            //adding 0s in back
            while (binaryForm.length()%3 != 0){
                binaryForm = binaryForm + "0";
            }
        }

        //creating groups of 3
        int tripletsNumber = binaryForm.length()/3;     //amount of groups to be created
        String [][] triplets = new String[tripletsNumber][3];    //[(a,b,c),(d,e,f),(g,h,i)], where the letters are 1's and 0's

        //separating the string to individual strings
        int element = 0;    //for the first element
        for (int row = 0; row < tripletsNumber; row++){
            for (int column = 0; column < 3; column++){
                triplets[row][column] = String.valueOf(binaryForm.charAt(element));
                element++;
            }
        }

        //converting every triplet to its octal equivalent
        String finalOctal = "";
        int individualOctal = 0;
        for (int row = 0; row < tripletsNumber; row++){
            if (triplets[row][0].equals("1")){
                individualOctal += 4;
            }
            if (triplets[row][1].equals("1")){
                individualOctal += 2;
            }
            if (triplets[row][2].equals("1")){
                individualOctal += 1;
            }
            finalOctal += individualOctal;
            individualOctal = 0;
        }

        return finalOctal;
    }

    /**
     *  Convert the decimal number to its decimal equivalent.
     *  ---------------------------------------------
     *  Basically removing unnecessary 0s on the decimal number.
     */
    private static String toDecimal(String decimalForm, char part){
        //getting rid of unnecessary 0s
        if (part == 'w'){
            while (decimalForm.charAt(0) == '0' && decimalForm.length() > 1){
                decimalForm = decimalForm.substring(1);
            }
        } else if (part == 'd'){
            while (decimalForm.charAt(decimalForm.length() - 1) == '0' && decimalForm.length() > 1){
                decimalForm = decimalForm.substring(0, decimalForm.length() - 1);
            }
        }

        return decimalForm;
    }

    /**
     *  Convert the decimal number to its hexadecimal equivalent.
     */
    private static String toHexadecimal(String decimalForm, char part){
        //transform the octal number to its binary equivalent
        String binaryForm = toBinary(decimalForm, part);

        //making the number of digits divisible by 4
        if (part == 'w'){
            //adding 0s in front
            while (binaryForm.length()%4 != 0){
                binaryForm = "0" + binaryForm;
            }
        } else if (part == 'd'){
            //adding 0s in back
            while (binaryForm.length()%4 != 0){
                binaryForm = binaryForm + "0";
            }   
        }

        //creating groups of 4
        int quadNumber = binaryForm.length()/4;
        String [][] quad = new String[quadNumber][4];

        //separating the string to individual strings
        int element = 0;
        for (int row = 0; row < quadNumber; row++){
            for (int column = 0; column < 4; column++){
                quad[row][column] = String.valueOf(binaryForm.charAt(element));
                element++;
            }
        }

        //converting every quad to its hexadecimal equivalent
        String finalHex = "";
        int individualHex = 0;
        for (int row = 0; row < quadNumber; row++){
            if (quad[row][0].equals("1")){
                individualHex += 8;
            }
            if (quad[row][1].equals("1")){
                individualHex += 4;
            }
            if (quad[row][2].equals("1")){
                individualHex += 2;
            }
            if (quad[row][3].equals("1")){
                individualHex += 1;
            }

            //turn digits 10 to 15 to their respective equivalent characters
            switch (individualHex) {
                case 10 -> finalHex += "A";
                case 11 -> finalHex += "B";
                case 12 -> finalHex += "C";
                case 13 -> finalHex += "D";
                case 14 -> finalHex += "E";
                case 15 -> finalHex += "F";
                default -> finalHex += individualHex;
            }

            individualHex = 0;
        }

        return finalHex;
    }
}
