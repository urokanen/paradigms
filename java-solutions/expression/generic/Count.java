package expression.generic;

public class Count<T> implements GenericExpression<T> {
    private final GenericExpression<T> expression;
    private final Operations<T> operations;

    public Count(GenericExpression<T> expression, Operations<T> operations) {
        this.expression = expression;
        this.operations = operations;
    }

    @Override
    public T evaluate(T value1, T value2, T value3) {
        T value = expression.evaluate(value1, value2, value3);
        if (value == null) {
            return null;
        }
        return operations.count(value);
    }
}
