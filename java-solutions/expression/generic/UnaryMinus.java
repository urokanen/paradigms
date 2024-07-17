package expression.generic;

public class UnaryMinus<T> implements GenericExpression<T> {
    private final GenericExpression<T> expression;
    private final Operations<T> operations;

    public UnaryMinus(GenericExpression<T> expression, Operations<T> operations) {
        this.expression = expression;
        this.operations = operations;
    }

    @Override
    public T evaluate(T value1, T value2, T value3) {
        T value = expression.evaluate(value1, value2, value3);
        if (value == null) {
            return null;
        }
        return operations.unary(value);
    }
}
