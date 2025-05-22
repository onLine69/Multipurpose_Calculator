package model.temperature;

import java.math.BigDecimal;

public class ReaumurConversion {
    public ReaumurConversion(){}
    
    public static String convertTo(String originalTemp, String toUnit){
        String converted = null;
        switch (toUnit) {
            case "Celsius":
                converted = toCelsius(originalTemp);
                break;
            case "Fahrenheit":
                converted = toFahrenheit(originalTemp);
                break;
            case "Kelvin":
                converted = toKelvin(originalTemp);
                break;
            case "Delisle":
                converted = toDelisle(originalTemp);
                break;
            case "Newton":
                converted = toNewton(originalTemp);
                break;
            case "Rankine":
                converted = toRankine(originalTemp);
                break;
            case "Reaumur":
                converted = toReaumur(originalTemp);
                break;
            case "Romer":
                converted = toRomer(originalTemp);
                break;
            default:
                converted = "Temperature Unit not Available.";
                break;
        }

        return converted;
    }

    /*
     * Convert to Celsius.
     * Formula: [°C] = 100 − [°De] × 2 ⁄ 3
     */
    private static String toCelsius(String originalTemp){
        originalTemp = toReaumur(originalTemp);
        
        BigDecimal converted = new BigDecimal("100");
        converted = converted.subtract(new BigDecimal(originalTemp));
        converted = converted.multiply(new BigDecimal("2"));
        converted = converted.divide(new BigDecimal("3"));

        return converted.toString();
    }

    /*
     * Convert to Fahrenheit.
     * Formula (in Celsius): [°F] = [°C] × 9 ⁄ 5 + 32
     */
    private static String toFahrenheit(String originalTemp){
        originalTemp = toCelsius(originalTemp);

        BigDecimal converted = new BigDecimal(originalTemp);
        converted = converted.multiply(new BigDecimal("1.8"));
        converted = converted.add(new BigDecimal("32"));

        return converted.toString();
    }

    /*
     * Convert to Kelvin.
     * Formula (in Celsius): [K] = [°C] + 273.15
     */
    private static String toKelvin(String originalTemp){
        originalTemp = toCelsius(originalTemp);

        BigDecimal converted = new BigDecimal(originalTemp);
        converted = converted.add(new BigDecimal("273.15"));

        return converted.toString();
    }

    /*
     * Convert to Delisle.
     * Formula (in Celsius): [°De] = (100 − [°C]) × 3 ⁄ 2
     */
    private static String toDelisle(String originalTemp){
        originalTemp = toCelsius(originalTemp);
        
        BigDecimal converted = new BigDecimal("100");
        converted = converted.subtract(new BigDecimal(originalTemp));
        converted = converted.multiply(new BigDecimal("1.5"));

        return converted.toString();
    }

    /*
     * Convert to Newton.
     * Formula (in Celsius): [°N] = [°C] × 33 ⁄ 100
     */
    private static String toNewton(String originalTemp){
        originalTemp = toCelsius(originalTemp);

        BigDecimal converted = new BigDecimal(originalTemp);
        converted = converted.multiply(new BigDecimal("0.33"));

        return converted.toString();
    }

    /*
     * Convert to Rankine.
     * Formula (in Celsius): [°R] = ([°C] + 273.15) × 9/5
     */
    private static String toRankine(String originalTemp){
        originalTemp = toCelsius(originalTemp);

        BigDecimal converted = new BigDecimal(originalTemp);
        converted = converted.add(new BigDecimal("273.15"));
        converted = converted.multiply(new BigDecimal("1.8"));

        return converted.toString();
    }

    /*
     * Reformat the temperature (e.g. remove all unnecessary 0s or other elements).
     */
    private static String toReaumur(String originalTemp){
        while (originalTemp.charAt(0) == '0' && originalTemp.charAt(1) != '.' && originalTemp.length() > 1){
            originalTemp = originalTemp.substring(1);
        }
        return originalTemp;
    }

    /*
     * Convert to Romer.
     * Formula (in Celsius): [°Rø] = [°C] × 21/40 + 7.5
     */
    private static String toRomer(String originalTemp){
        originalTemp = toCelsius(originalTemp);
        
        BigDecimal converted = new BigDecimal(originalTemp);
        converted = converted.multiply(new BigDecimal("0.525"));
        converted = converted.add(new BigDecimal("7.5"));

        return converted.toString();
    }
}
