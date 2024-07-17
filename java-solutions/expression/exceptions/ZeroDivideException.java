package expression.exceptions;

public class ZeroDivideException extends ExpressionException {
    public ZeroDivideException() {
        super("There was a division by 0");
    }
}
