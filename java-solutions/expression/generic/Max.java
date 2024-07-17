package expression.generic;

public class Max<T> extends BinaryOperation<T> {
    public Max(GenericExpression<T> element1, GenericExpression<T> element2, Operations<T> operations) {
        super(element1, element2, operations);
    }

    @Override
    protected T calculate(T value1, T value2) {
        return operations.max(value1, value2);
    }
}
