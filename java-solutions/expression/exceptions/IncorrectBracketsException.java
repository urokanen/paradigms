package expression.exceptions;

public class IncorrectBracketsException extends ExpressionException {
    public IncorrectBracketsException(String operator) {
        super("expected bracket: " + operator);
    }
}
