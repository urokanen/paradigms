package expression.generic;

public class Variable<T> implements GenericExpression<T> {
    private final String title;

    public Variable(String title) {
        this.title = title;
    }

    @Override
    public T evaluate(T value1, T value2, T value3) {
        return switch (title) {
            case "x" -> value1;
            case "y" -> value2;
            case "z" -> value3;
            default -> null;
        };
    }
}
