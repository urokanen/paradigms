package expression.exceptions;

public class IncorrectNumberException extends ExpressionException {
    public IncorrectNumberException() {
        super("This number is larger than the size of the int type");
    }
}
