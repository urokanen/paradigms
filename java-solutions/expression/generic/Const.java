package expression.generic;

public class Const<T> implements GenericExpression<T> {
    private final Operations<T> operations;
    private final int value;

    public Const(int value, Operations<T> operations) {
        this.operations = operations;
        this.value = value;
    }

    @Override
    public T evaluate(T value1, T value2, T value3) {
        return operations.cast(this.value);
    }
}
