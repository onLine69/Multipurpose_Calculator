package model.basicCalculator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalcuEvaluate {
    public CalcuEvaluate(){}

    public static String evaluateExpression(String expression){
        String finalResult;
        /*
        Separate every operator.
        */
        String inputOperators = "";
        for (int exChar = 0; exChar < expression.length(); exChar++){
            if (expression.charAt(exChar) == '+' || expression.charAt(exChar) == '-' || expression.charAt(exChar) == 'x' 
            || expression.charAt(exChar) == '/' || expression.charAt(exChar) == '^'){
                if (exChar != 0 || expression.charAt(0) != '-'){
                    inputOperators += expression.charAt(exChar);
                }
            }
        }

        /*
        Create an array with every operator in the expression.
        */
        String[] operators = new String[inputOperators.length()];
        for (int exOpe = 0; exOpe < inputOperators.length(); exOpe++){
            operators[exOpe] = String.valueOf(inputOperators.charAt(exOpe));
        }

        /*
        Create an array with every number in the expression.
        */
        String[] numbers = new String[inputOperators.length() + 1];
        int numberCount = 0;

        for (int exChar = 0; exChar < expression.length(); exChar++){
            if ((expression.charAt(exChar) != '+' && expression.charAt(exChar) != '-' && expression.charAt(exChar) != 'x' 
            && expression.charAt(exChar) != '/' && expression.charAt(exChar) != '^')
            || (exChar == 0 && expression.charAt(0) == '-')){
                if (numbers[numberCount] == null) numbers[numberCount] = "";
                numbers[numberCount] += expression.charAt(exChar);
            } else {
                numberCount++;
            }
        }

        finalResult = expEvaluate(numbers, operators, theresExp(operators));

        return finalResult;
    }

    private static int theresExp(String[] operators){
        int whereExp = -1;  //if there is no exponential operation

        for (int opCount = 0; opCount < operators.length; opCount++){
            if (operators[opCount].equals("^")){
                whereExp = opCount;
                break;
            }
        }
        return whereExp;

    }

    private static String expEvaluate(String[] numbers, String[] operators, int whereExp){
        String[] resultNumbers = numbers;
        String[] resultOperators = operators;
        String finalResult;

        if (resultNumbers.length == 1){
            return resultNumbers[0];
        }

        if (whereExp >= 0){
            String evalResult = evalTwoNum(resultNumbers[whereExp], resultOperators[whereExp], resultNumbers[whereExp + 1]);
            resultNumbers = rearranger('n', resultNumbers, whereExp, evalResult);
            resultOperators = rearranger('o', resultOperators, whereExp, evalResult);

            finalResult = expEvaluate(resultNumbers, resultOperators, theresExp(resultOperators));
        } else {
            finalResult = MDEvaluate(resultNumbers, resultOperators, theresMD(resultOperators));
        }

        return finalResult;
    }

    /*
    * Check whether there is a Multiplication and/or Division operation/s, and locate where.
    */
    private static int theresMD(String[] operators){
        int whereMD = -1;

        for (int opCount = 0; opCount < operators.length; opCount++){
            if (operators[opCount].equals("x") || operators[opCount].equals("/")){
                whereMD = opCount;
                break;
            }
        }

        return whereMD;
    }

    /*
    * Evaluate the Multiplication and Division operations in the expression.
    */
    private static String MDEvaluate(String[] numbers, String[] operators, int whereMD){
        String[] resultNumbers = numbers;
        String[] resultOperators = operators;
        String finalResult;

        if (resultNumbers.length == 1){
            return resultNumbers[0];
        } 
        
        if (whereMD >= 0){
            String evalResult = evalTwoNum(resultNumbers[whereMD], resultOperators[whereMD], resultNumbers[whereMD + 1]);
            resultNumbers = rearranger('n', resultNumbers, whereMD, evalResult);
            resultOperators = rearranger('o', resultOperators, whereMD, evalResult);

            //use recurssion to assure that all M/D operations are simplfied
            finalResult = MDEvaluate(resultNumbers, resultOperators, theresMD(resultOperators));    
        } else {
            finalResult = ASEvaluate(resultNumbers, resultOperators);
        }

        return finalResult;
    }

    /*
    * Evaluate the Addition and Subtraction operations in the expression.
    */
    private static String ASEvaluate(String[] numbers, String[] operators){
        String[] resultNumbers = numbers;
        String[] resultOperators = operators;
        String finalResult;

        if (numbers.length == 1){
            finalResult = numbers[0];
        } else {
            String evalResult = evalTwoNum(numbers[0], operators[0], numbers[1]);
            resultNumbers = rearranger('n', numbers, 0, evalResult);
            resultOperators = rearranger('o', operators, 0, evalResult);

            //use recurssion to assure that all A/S operations are simplfied
            finalResult = ASEvaluate(resultNumbers, resultOperators);
        }
        return finalResult;
    }

    /*
    * Rearranges the arrays for numbers and operations after simplifying operations so that there will be no 
    * duplicates or empty cell in the arrays.
    */
    private static String[] rearranger(char indicator, String[] arr, int count, String add){
        String[] newArr = new String[arr.length - 1];

        //'o' for operator and 'n' for numbers
        switch (indicator) {
            case 'o' -> {
                for (int copy = 0, move = 0; copy < arr.length; copy++){
                    if (copy == count) continue;

                    newArr[move++] = arr[copy];
                }
            }
            case 'n' -> {
                for (int copy = 0; copy < count; copy++){
                    newArr[copy] = arr[copy];
                }

                newArr[count] = add;

                for (int move = count + 1; move < newArr.length; move++){
                    newArr[move] = arr[move + 1];
                }
            }
        }

        return newArr;
    }

    /*
     * Calculate the square root of a number.
     */
    public static String squareRoot(String expression){
        return new BigDecimal(expression).sqrt(new MathContext(19,RoundingMode.HALF_EVEN)).toString();
    }

    /*
     * Evaluates the two numbers based on the operation
     */
    //TODO: Fix exp for double exponent
    private static String evalTwoNum(String firstNumber, String operator, String secondNumber){
        BigDecimal solveResult = new BigDecimal(firstNumber);

        //evaluate the expressions
        solveResult = switch (operator) {
            case "+" -> solveResult.add(new BigDecimal(secondNumber));
            case "-" -> solveResult.subtract(new BigDecimal(secondNumber));
            case "x" -> solveResult.multiply(new BigDecimal(secondNumber));
            case "/" -> solveResult.divide(new BigDecimal(secondNumber), 19, RoundingMode.HALF_EVEN);  //TODO: needed to be upgraded in the future
            case "^" -> solveResult.pow(Integer.parseInt(secondNumber));
            default -> throw new IllegalArgumentException("Operator Not Valid");
        };

        String result = solveResult.toString();
        int separateE = CalcuGroups.extractSciNota(result);     //check if the result is in scientific notation

        if (separateE > 0){ //if a scientific notation
            String decimalPart = result.substring(0, separateE - 1);    //separate the coefficient part of the scientific notation
            
            //get rid of unecessary zeroes
            if (isDecimal(decimalPart)){
                while (decimalPart.charAt(decimalPart.length() - 1) == '0' && decimalPart.length() > 1){
                    decimalPart = decimalPart.substring(0, decimalPart.length() - 1);
                }
                if (decimalPart.charAt(decimalPart.length() - 1) == '.' && decimalPart.length() > 1){
                    decimalPart = decimalPart.substring(0, decimalPart.length() - 1);
                }
            }

            if (decimalPart.length() == 1){
                decimalPart += ".00";   //if after cleaning will only leave a whole number 
            }
            result = decimalPart + result.substring(separateE - 1);
        } else {
            //get rid of unecessary zeroes
            if (isDecimal(result)){
                while (result.charAt(result.length() - 1) == '0' && result.length() > 1){
                    result = result.substring(0, result.length() - 1);
                }
                if (result.charAt(result.length() - 1) == '.' && result.length() > 1){
                    result = result.substring(0, result.length() - 1);
                }
            }
        }
        
        return result;
    }

    /*
    * Check if the input number is a decimal.
    */
    private static boolean isDecimal(String number){
        int decimalLocation = number.indexOf(".");
        
        return decimalLocation != -1;
    }
}