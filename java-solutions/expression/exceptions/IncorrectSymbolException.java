package expression.exceptions;

public class IncorrectSymbolException extends ExpressionException {
    public IncorrectSymbolException(String operator, int pos) {
        super("Incorrect operation found: " + operator + " at position: " + pos);
    }
}
