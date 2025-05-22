package model.programmerCalculator;
import control.programmerCalculator.*;

public class NumberSystemConverter {
    public NumberSystemConverter(){}

    /**
     * This class separates the whole number part and the decimal part then converts them separately.
     * It also checks whether the conversion is possible before starting the conversion process.
     * After conversion, if it is negative, it combines the sign '-' + the whole number part + dot '.' + the decimal number part.
     * Returns the conbined string.
     * 
     * @param originalNumber
     * @param originalNumberBaseSystem
     * @param baseSystemToConvertTo
     * @return convertedNumber
     */
    public static String convertTo(String originalNumber, String originalNumberBaseSystem, String baseSystemToConvertTo){
        String convertedNumber = "";
        try{
            String convertedWholeNumber = "";
            String convertedDecimal = "";

            String wholeNumberPart = originalNumber;
            String sign = "";
            String dot = "";
            String decimalNumberPart = "";   //after the dot

            /*
             * Check whether there is a dot and separate the whole number from decimal part.
             */
            for (int character = 0; character < wholeNumberPart.length(); character++){
                if (originalNumber.charAt(character) == '.'){
                    dot = ".";
                    decimalNumberPart = wholeNumberPart.substring(character + 1);
                    wholeNumberPart = wholeNumberPart.substring(0, character);
                    break;
                }
            }

            /*
             * Checks whether the first character is '-' then separating it from the whole number.
             */
            if (wholeNumberPart.charAt(0) == '-'){
                sign = "-";
                wholeNumberPart = wholeNumberPart.substring(1);
            }

            /*
             * Checks whether the conversion/s are possible given the numbers and their number system.
             */
            isConversionPossible(wholeNumberPart, originalNumberBaseSystem);
            isConversionPossible(decimalNumberPart, originalNumberBaseSystem);

            switch (originalNumberBaseSystem){
                case "Binary":
                    convertedWholeNumber = BinaryConversion.convertTo(wholeNumberPart, baseSystemToConvertTo, 'w');
                    convertedDecimal = BinaryConversion.convertTo(decimalNumberPart, baseSystemToConvertTo, 'd');
                    break;
                case "Octal":
                    convertedWholeNumber = OctalConversion.convertTo(wholeNumberPart, baseSystemToConvertTo, 'w');
                    convertedDecimal = OctalConversion.convertTo(decimalNumberPart, baseSystemToConvertTo, 'd');
                    break;
                case "Decimal":
                    convertedWholeNumber = DecimalConversion.convertTo(wholeNumberPart, baseSystemToConvertTo, 'w');
                    convertedDecimal = DecimalConversion.convertTo(decimalNumberPart, baseSystemToConvertTo, 'd');
                    break;
                case "Hexadecimal":
                    convertedWholeNumber = HexadecimalConversion.convertTo(wholeNumberPart, baseSystemToConvertTo, 'w');
                    convertedDecimal = HexadecimalConversion.convertTo(decimalNumberPart, baseSystemToConvertTo, 'd');
                    break;
                default:
                    //if the chosen number system of the original number is not listed (not recheable).
                    convertedWholeNumber = "Number System ";
                    convertedDecimal = "not Listed";
                    break;
            }

            convertedNumber = sign + convertedWholeNumber + dot + convertedDecimal;
        } catch (NumberNotBelongException e){
            //when the original number doesn't belong to the chosen number system
            convertedNumber = String.valueOf(e.getMessage());   
        } catch (Exception ex){
            //when the input number isn't written properly e.g. .3 when it is suppose to be 0.3
            convertedNumber = "Write the Number Properly.";
        }

        return convertedNumber;
    }

    /**
     * Checks whether the conversion is possible based on the original number.
     * 
     * @param originalNumber
     * @param originalNumberBaseSystem
     * @throws NumberNotBelongException
     */
    private static void isConversionPossible(String originalNumber, String originalNumberBaseSystem) throws NumberNotBelongException{
        if (!originalNumberIsBinary(originalNumber, originalNumberBaseSystem) && !originalNumberIsOctal(originalNumber, originalNumberBaseSystem) && !originalNumberIsDecimal(originalNumber, originalNumberBaseSystem) && !originalNumberIsHexadecimal(originalNumber, originalNumberBaseSystem)){
            throw new NumberNotBelongException("Number Input Not Valid.");
        }
    }

    /**
     * Check whether the original number belongs to the Binary Number System.
     */
    private static boolean originalNumberIsBinary(String originalNumber, String originalNumberBaseSystem){
        if (originalNumberBaseSystem != "Binary"){
            return false;
        }

        //checks whether all the digits are only 0s and 1s
        for (int element = 0; element < originalNumber.length(); element++){
            if (originalNumber.charAt(element) != '1' && originalNumber.charAt(element) != '0'){
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether the original number belongs to the Octal Number System.
     */
    private static boolean originalNumberIsOctal(String originalNumber, String originalNumberBaseSystem){
        if (originalNumberBaseSystem != "Octal"){
            return false;
        }

        //checks whether all the digits are from 0 to 7
        for (int element = 0; element < originalNumber.length(); element++){
            if (originalNumber.charAt(element) < '0' || originalNumber.charAt(element) > '7'){
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether the original number belongs to the Decimal Number System.
     */
    private static boolean originalNumberIsDecimal(String originalNumber, String originalNumberBaseSystem){
        if (originalNumberBaseSystem != "Decimal"){
            return false;
        }

        //checks whether all the digits are from 0 to 9
        for (int element = 0; element < originalNumber.length(); element++){
            if (originalNumber.charAt(element) < '0' || originalNumber.charAt(element) > '9'){
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether the original number belongs to the Hexadcimal Number System.
     */
    private static boolean originalNumberIsHexadecimal(String originalNumber, String originalNumberBaseSystem){
        if (originalNumberBaseSystem != "Hexadecimal"){
            return false;
        }

        //checks whether all the digits are from 0 to 9 or A to F 
        for (int element = 0; element < originalNumber.length(); element++){
                if ((originalNumber.charAt(element) < '0' || originalNumber.charAt(element) > '9') && (originalNumber.charAt(element) < 'A' || originalNumber.charAt(element) > 'F')){
                    return false;
                }
        }

        return true;
    }
}
