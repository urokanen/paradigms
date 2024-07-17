package expression.exceptions;

public class OverflowException extends ExpressionException {
    public OverflowException(String operation) {
        super("An overflow was detected in the operation: " + operation);
    }
}
