package model.basicCalculator;
import java.util.Stack;

public class CalcuGroups {
    public CalcuGroups(){}

    public static String simplifyExpression(String expression){
        if (!isGroupValid(expression)) return "Not Valid";
        
        expression = modifyExpression(expression);

        Stack<Character> openGroups = new Stack<>();
        String toSimplify = ""; //where grouped expression are added

        for (int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) != ')'){   //add the character to the stack if not a close parenthesis
                openGroups.push(expression.charAt(i));
            } else {    //simplify the group under the present close parenthesis up to the latest open parenthesis
                while (openGroups.peek() != '('){
                    //add every character except the open parenthesis to the string in reverse (latest out, most left of the string)
                    toSimplify = openGroups.pop() + toSimplify; 
                }

                toSimplify = CalcuEvaluate.evaluateExpression(toSimplify);  //simplify the group
                openGroups.pop();   //get rid of the latest open parenthesis

                //add the simplified result back to the stack
                for (int j = 0; j < toSimplify.length(); j++){
                    openGroups.push(toSimplify.charAt(j));
                }
                
                toSimplify = "";    //reset the string
            }
        }
        //add the final group to be simplified
        while (!openGroups.isEmpty()){
            toSimplify = openGroups.pop() + toSimplify;
        }
        
        return CalcuEvaluate.evaluateExpression(toSimplify);
    }

    private static boolean isGroupValid(String expression){
        int countOpen = 0;  //count open parenthesis
        int countClose = 0; //count close parenthesis

        //loops at the expression to check if the groupings are valid
        for (int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) == '(') countOpen++;
            if (expression.charAt(i) == ')') countClose++;

            //close parenthesis must be less or equal to open
            if (countClose > countOpen) return false;
        }

        return countOpen == countClose;
    }

    /*
     * Modify the expression so that it is solvable.
     */
    public static String modifyExpression(String expression){
        if (expression.indexOf("E") != -1){
            expression = modifyExponentialExpression(expression, expression.indexOf("E"));
        }
        
        expression = modifyDecimalPoints(expression);
        return expression;
    }
    
    public static String modifyExponentialExpression(String expression, int whereE){       
        if (whereE != -1){
            String base = expression.substring(0, whereE);
            String exponent = expression.substring(whereE + 1);

            String anotherExpression = "";
            int newOperation = extractSciNota(exponent);    //where the sci notation end
            if (newOperation != -1){
                anotherExpression = exponent.substring(newOperation);
                exponent = exponent.substring(0, newOperation);
            }

            char whole = base.charAt(0);
            String decimal = base.substring(2);
            
            //if a negative exponent
            if (exponent.charAt(0) == '-'){
                base = whole + decimal;
                for (int e = 0; e < Integer.valueOf(exponent.substring(1)) - 1; e++){
                    base = "0" + base;
                }
                expression = "0." + base + anotherExpression;
            } else {
                int movePoint = Integer.valueOf(exponent) - decimal.length();
                if (movePoint == 0){
                    expression = whole + decimal + ".00" + anotherExpression;
                } else if (movePoint > 0){
                    expression = whole + decimal;
                    for (int e = 0; e < movePoint; e++){
                        expression += "0";
                    }
                    expression += ".00" + anotherExpression;
                } else {
                    int movePointRight = Integer.valueOf(exponent);
                    expression = whole + decimal.substring(0, movePointRight) + "." + decimal.substring(movePointRight) + anotherExpression;
                }
            }
        }

        return expression;
    }

    /*
     * Locate where scientific notation start and end.
     */
    public static int extractSciNota(String exponent){
        char[] operators = {'+', '-', 'x', '/'};
        for (int i = 0; i < exponent.length(); i++){
            if (i == 0 && exponent.charAt(i) == '-'){
                continue;
            }
            
            for (char op : operators){
                if (exponent.charAt(i) == op){
                    return i;
                }
            }
        }
        return -1;
    }

    /*
     * Modify expressions with decimal points.
     */
    private static String modifyDecimalPoints(String expression){
        //if point is at the beginning 
        if (expression.charAt(0) == '.'){
            expression = "0" + expression;
        }

        //if point is at the end
        if (expression.charAt(expression.length() - 1) == '.'){
            expression += "0";
        }

        int j = 0;
        while (j < expression.length()){
            for (int i = 0; i < expression.length(); i++){
                //if the character in the left of the point is not a number
                if (expression.charAt(i) == '.' && (expression.charAt(i - 1) < '0' || expression.charAt(i - 1) > '9')){
                    expression = expression.substring(0, i) + "0" + expression.substring(i);
                    break;
                }
                
                //if the character in the right of the point is not a number
                if (expression.charAt(i) == '.' && (expression.charAt(i + 1) < '0' || expression.charAt(i + 1) > '9')){
                    expression = expression.substring(0, i + 1) + "0" + expression.substring(i + 1);
                    break;
                }
            }
            j++;
        }

        return expression;
    }
}