package expression.generic;

public abstract class BinaryOperation<T> implements GenericExpression<T> {
    private final GenericExpression<T> element1;
    private final GenericExpression<T> element2;
    protected final Operations<T> operations;

    public BinaryOperation(GenericExpression<T> element1, GenericExpression<T> element2, Operations<T> operations) {
        this.element1 = element1;
        this.element2 = element2;
        this.operations = operations;
    }

    protected abstract T calculate(T value1, T value2);

    @Override
    public T evaluate(T value1, T value2, T value3) {
        T left = element1.evaluate(value1, value2, value3);
        T right = element2.evaluate(value1, value2, value3);
        if (left == null || right == null) {
            return null;
        }
        return calculate(left, right);
    }
}
