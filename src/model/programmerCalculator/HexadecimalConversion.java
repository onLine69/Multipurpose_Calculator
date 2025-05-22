package model.programmerCalculator;
import java.math.*;

public class HexadecimalConversion {
    public HexadecimalConversion(){}

    public static String convertTo(String hexaForm, String newNumberSystem, char part){
        String convertedValue = "";   //no initial value

        /*
         * Checks first whether the argument is empty, then return an empty string.
         * When the argument is "0", returns "0".
         * And if the argument isn't one of those, convert the number to the desired sumber system. 
         */
        if (hexaForm.equals("")){
            convertedValue = "";
        } else {
            hexaForm = toHexadecimal(hexaForm, part);    //getting rid of the unnecessary 0s

            if (hexaForm.equals("0")){
                convertedValue = "0";
            } else {
                switch (newNumberSystem){
                    case "Binary":
                        convertedValue = toBinary(hexaForm, part);
                        break;
                    case "Octal":
                        convertedValue = toOctal(hexaForm, part);
                        break;
                    case "Decimal":
                        convertedValue = toDecimal(hexaForm, part);
                        break;
                    case "Hexadecimal":
                        convertedValue = toHexadecimal(hexaForm, part);
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
     *  Convert the hexadecimal number to its binary equivalent.
     */
    private static String toBinary(String hexaForm, char part){
        //separate every digit
        //transform digits from A - F to its decimal counterpart (10 - 15)
        String[] indivNumber = new String[hexaForm.length()];
        for (int digitNumber = 0; digitNumber < hexaForm.length(); digitNumber++){
            switch (String.valueOf(hexaForm.charAt(digitNumber))) {
                case "A" -> indivNumber[digitNumber] = "10";
                case "B" -> indivNumber[digitNumber] = "11";
                case "C" -> indivNumber[digitNumber] = "12";
                case "D" -> indivNumber[digitNumber] = "13";
                case "E" -> indivNumber[digitNumber] = "14";
                case "F" -> indivNumber[digitNumber] = "15";
                default -> indivNumber[digitNumber] = String.valueOf(hexaForm.charAt(digitNumber));
            }
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
        String[][] convertBinary = new String[indivNumber.length][4];
        int intIndivNumber;
        for (int row = 0; row < indivNumber.length; row++){
            intIndivNumber = Integer.parseInt(indivNumber[row]);
            for (int column = 3; column >= 0; column--){
                convertBinary[row][column] = String.valueOf(intIndivNumber%2);
                intIndivNumber /= 2;
                if(part == 'w' && row == 0 && intIndivNumber == 0) break;  //if the number is already 0 (so that it won't add unnecessary 0s in the array)
            }
        }

        //transform the 2d array to a string, also removing the "null" in the array if there are some
        String finalBinary = "";
        for (int row = 0; row < indivNumber.length; row++){
            for (int column = 0; column < 4; column++){
                if (convertBinary[row][column] == null) finalBinary += "";
                else finalBinary += convertBinary[row][column];
            }
        }

        return finalBinary;
    }

    /**
     *  Convert the hexadecimal number to its octal equivalent.
     */
    private static String toOctal(String hexaForm, char part){
        //transform the hexadecimal number to its binary equivalent
        String binaryForm = toBinary(hexaForm, part);

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
     *  Convert the hexadecimal number to its decimal equivalent.
     */
    private static String toDecimal(String hexaForm, char part){
        //transform the octal number to its binary equivalent
        String binaryForm = toBinary(hexaForm, part);

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
     * Convert the hexadecimal number to its hexadecimal form.
     * ---------------------------------------------
     * Basically removing unnecessary 0s on the hexadecimal number.
     */
    private static String toHexadecimal(String hexaForm, char part){
        //getting rid of unnecessary 0s
        if (part == 'w'){
            while (hexaForm.charAt(0) == '0' && hexaForm.length() > 1){
                hexaForm = hexaForm.substring(1);
            }
        } else if (part == 'd'){
            while (hexaForm.charAt(hexaForm.length() - 1) == '0' && hexaForm.length() > 1){
                hexaForm = hexaForm.substring(0, hexaForm.length() - 1);
            }
        }

        return hexaForm;
    }
}
