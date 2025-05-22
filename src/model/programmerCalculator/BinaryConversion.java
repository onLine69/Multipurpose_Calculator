package model.programmerCalculator;
import java.math.*;

public class BinaryConversion {
    public BinaryConversion(){}

    public static String convertTo(String binaryForm, String newNumberSystem, char part){
        String convertedValue = "";

        /*
         * Checks first whether the argument is empty, then return an empty string.
         * When the argument is "0", returns "0".
         * And if the argument isn't one of those, convert the number to the desired sumber system. 
         */
        if (binaryForm.equals("")){
            convertedValue = "";
        } else {
            binaryForm = toBinary(binaryForm, part);    //getting rid of the unnecessary 0s

            if (binaryForm.equals("0")){
                convertedValue = "0";
            } else {
                switch (newNumberSystem){
                    case "Binary":
                        convertedValue = toBinary(binaryForm, part);
                        break;
                    case "Octal":
                        convertedValue = toOctal(binaryForm, part);
                        break;
                    case "Decimal":
                        convertedValue = toDecimal(binaryForm, part);
                        break;
                    case "Hexadecimal":
                        convertedValue = toHexadecimal(binaryForm, part);
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
     * Convert the binary number to its binary form.
     * ---------------------------------------------
     * Basically removing unnecessary 0s on the binary number.
     */
    private static String toBinary(String binaryForm, char part){
        //getting rid of unnecessary 0s
        if (part == 'w'){
            while (binaryForm.charAt(0) == '0' && binaryForm.length() > 1){
                binaryForm = binaryForm.substring(1);
            }
        } else if (part == 'd'){
            while (binaryForm.charAt(binaryForm.length() - 1) == '0' && binaryForm.length() > 1){
                binaryForm = binaryForm.substring(0, binaryForm.length() - 1);
            }
        }

        return binaryForm;
    }

    /**
     *  Convert the binary number to its octal equivalent.
     */
    private static String toOctal(String binaryForm, char part){
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
     *  Convert the binary number to its decimal equivalent.
     */
    private static String toDecimal(String binaryForm, char part){
        String decimalForm = "";

        if (part == 'w'){
            //create initial value for the decimal form = 0
            BigInteger decimalFormWhole = new BigInteger("0");   //to handle really large numbers

            BigInteger powerWhole = new BigInteger("2");;     //add 2^n where n is the position of the number being scanned from the right to the decimalForm
            
            int expo = 0;   //location of the 1 in the sequence 

            /*
            Step 1: Check whether the character is 1
            Step 2: If 1, add 2^n where n is the position of the character from the right to the decimalForm
            Step 3: If 0, add 0
            Step 4: Move to the next character to the left
            Step 5: Repeat
            */
            for (int element = binaryForm.length() - 1; element >= 0; element--){
                if (binaryForm.charAt(element) == '1'){
                    powerWhole = powerWhole.pow(expo);
                    decimalFormWhole = decimalFormWhole.add(powerWhole);
                    powerWhole = new BigInteger("2");
                }
                expo++;     //add 1 every time it moves to another character
            }
            
            decimalForm = String.valueOf(decimalFormWhole);
        } else if (part == 'd'){
            //create initial value for the decimal form = 0
            BigDecimal decimalFormDecimal = new BigDecimal("0");    //to handle really large numbers
            BigDecimal powerDecimal = new BigDecimal("0.5");    //add (1/2)^n where n is the position of the number being scanned from the right to the decimalForm
            int expo = 1;      //location of the 1 in the sequence

            /*
            Step 1: Check whether the character is 1
            Step 2: If 1, add (1/2)^n where n is the position of the character from the right to the decimalForm
            Step 3: If 0, add 0
            Step 4: Move to the next character to the left
            Step 5: Repeat
            */
            for (int element = 0; element < binaryForm.length(); element++){
                if (binaryForm.charAt(element) == '1'){
                    powerDecimal = powerDecimal.pow(expo);
                    decimalFormDecimal = decimalFormDecimal.add(powerDecimal);
                    powerDecimal = new BigDecimal("0.5");
                }  
                expo++;     //add 1 every time it moves to another character
            }
            decimalForm = String.valueOf(decimalFormDecimal).substring(2);
        }

        return decimalForm;
    }

    /**
     *  Convert the binary number to its hexadecimal equivalent.
     */
    private static String toHexadecimal(String binaryForm, char part){
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
