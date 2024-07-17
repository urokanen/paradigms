package expression.generic;

public class Min<T> extends BinaryOperation<T> {
    public Min(GenericExpression<T> element1, GenericExpression<T> element2, Operations<T> operations) {
        super(element1, element2, operations);
    }

    @Override
    protected T calculate(T value1, T value2) {
        return operations.min(value1, value2);
    }
}
