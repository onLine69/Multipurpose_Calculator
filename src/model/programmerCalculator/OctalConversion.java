package model.programmerCalculator;
import java.math.*;

public class OctalConversion {
    public OctalConversion(){}

    public static String convertTo(String octalForm, String newNumberSystem, char part){
        String convertedValue = "";

        /*
         * Checks first whether the argument is empty, then return an empty string.
         * When the argument is "0", returns "0".
         * And if the argument isn't one of those, convert the number to the desired sumber system. 
         */
        if (octalForm.equals("")){
            convertedValue = "";
        } else {
            octalForm = toOctal(octalForm, part);    //getting rid of the unnecessary 0s

            if (octalForm.equals("0")){
                convertedValue = "0";
            } else {
                switch (newNumberSystem){
                    case "Binary":
                        convertedValue = toBinary(octalForm, part);
                        break;
                    case "Octal":
                        convertedValue = toOctal(octalForm, part);
                        break;
                    case "Decimal":
                        convertedValue = toDecimal(octalForm, part);
                        break;
                    case "Hexadecimal":
                        convertedValue = toHexadecimal(octalForm, part);
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
     *  Convert the octal number to its binary equivalent.
     */
    private static String toBinary(String octalForm, char part){
        //separate every digit
        String[] indivNumber = new String[octalForm.length()];
        for (int digitNumber = 0; digitNumber < octalForm.length(); digitNumber++){
            indivNumber[digitNumber] = String.valueOf(octalForm.charAt(digitNumber));
        }

        //convert every digit to their respective binary equivalents
        /*
            Step 1: Get the remainder of the digit when divided by 2
            Step 2: Place the remainder to the first column on the row
            Step 3: Divide the digit by 2
            Step 4: Repeat steps 1 to 3, moving 1 column at a time until
                    all the columns on the row are filled.
            Step 5: Move to the next digit and row.
            Step 6: Repeat
         */
        String[][] convertBinary = new String[indivNumber.length][3];
        int intIndivNumber;
        for (int row = 0; row < indivNumber.length; row++){
            intIndivNumber = Integer.parseInt(indivNumber[row]);

            for (int column = 2; column >= 0; column--){
                convertBinary[row][column] = String.valueOf(intIndivNumber%2);
                intIndivNumber /= 2;

                if(part == 'w' && row == 0 && intIndivNumber == 0) break;  //if the number is already 0 (so that it won't add unnecessary 0s in the array)
            }
        }

        //transform the 2d array to a string, also removing the "null" in the array if there are some
        String finalBinary = "";
        for (int row = 0; row < indivNumber.length; row++){
            for (int column = 0; column < 3; column++){
                if (convertBinary[row][column] == null) finalBinary += "";
                else finalBinary += convertBinary[row][column];
            }
        }

        return finalBinary;
    }

    /**
     * Convert the octal number to its octal form.
     * ---------------------------------------------
     * Basically removing unnecessary 0s on the octal number.
     */
    private static String toOctal(String octalForm, char part){
        //getting rid of unnecessary 0s
        if (part == 'w'){
            while (octalForm.charAt(0) == '0' && octalForm.length() > 1){
                octalForm = octalForm.substring(1);
            }
        } else if (part == 'd'){
            while (octalForm.charAt(octalForm.length() - 1) == '0' && octalForm.length() > 1){
                octalForm = octalForm.substring(0, octalForm.length() - 1);
            }
        }

        return octalForm;
    }

    /**
     *  Convert the octal number to its decimal equivalent.
     */
    private static String toDecimal(String octalForm, char part){
        //transform the octal number to its binary equivalent
        String binaryForm = toBinary(octalForm, part);

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
     *  Convert the octal number to its hexadecimal equivalent.
     */
    private static String toHexadecimal(String octalForm, char part){
        //transform the octal number to its binary equivalent
        String binaryForm = toBinary(octalForm, part);

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
