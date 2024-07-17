package expression.exceptions;

public class UnknownVariableException extends ExpressionException {
    public UnknownVariableException() {
        super("Incorrect variable found!");
    }
}
