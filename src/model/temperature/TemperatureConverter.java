package model.temperature;

public class TemperatureConverter {
    public TemperatureConverter() {}

    public String convertTo(String originalTemp, String fromUnit, String toUnit) {
        String converted = "";
        if (!validator(originalTemp)){
            converted = "Inputed Number not Valid";
        } else {
            switch (fromUnit) {
                case "Celsius":
                    converted = CelsiusConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Fahrenheit":
                    converted = FahrenheitConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Kelvin":
                    converted = KelvinConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Delisle":
                    converted = DelisleConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Newton":
                    converted = NewtonConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Rankine":
                    converted = RankineConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Reaumur":
                    converted = ReaumurConversion.convertTo(originalTemp, toUnit);
                    break;
                case "Romer":
                    converted = RomerConversion.convertTo(originalTemp, toUnit);
                    break;
                default:
                    converted = "Temperature Unit not Available.";
                    break;
            }
        }

        return converted;
    }

    private boolean validator(String originalTemp) {
        String wholeNumberPart = originalTemp;
        String decimalNumberPart = ""; // after the dot

        /*
         * Check whether there is a dot and separate the whole number from decimal part.
         */
        for (int character = 0; character < wholeNumberPart.length(); character++) {
            if (originalTemp.charAt(character) == '.') {
                decimalNumberPart = wholeNumberPart.substring(character + 1);
                wholeNumberPart = wholeNumberPart.substring(0, character);
                break;
            }
        }

        /*
         * Checks whether the first character is '-' then separating it from the whole
         * number.
         */
        if (wholeNumberPart.charAt(0) == '-') {
            wholeNumberPart = wholeNumberPart.substring(1);
        }

        //check the whole number part
        for (int whole = 0; whole < wholeNumberPart.length(); whole++){
            if (wholeNumberPart.charAt(whole) < '0' || wholeNumberPart.charAt(whole) > '9'){
                return false;
            }
        }

        //check the decimal number part
        for (int decimal = 0; decimal < decimalNumberPart.length(); decimal++){
            if (decimalNumberPart.charAt(decimal) < '0' || decimalNumberPart.charAt(decimal) > '9'){
                return false;
            }
        }

        return true;

    }
}
