package control.programmerCalculator;

/**
 * Custom exception that thrown when the number doesn't belong to any listed base system.
 *
 * @author Caine Ivan R. Bautista
 * @date June 02, 2023
 */
public class NumberNotBelongException extends Exception{
    public NumberNotBelongException(String exceptionMessage){
        super(exceptionMessage);
    }
}