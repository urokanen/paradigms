package expression.generic;

public class Add<T> extends BinaryOperation<T> {
    public Add(GenericExpression<T> element1, GenericExpression<T> element2, Operations<T> operations) {
        super(element1, element2, operations);
    }

    @Override
    protected T calculate(T value1, T value2) {
        return operations.add(value1, value2);
    }
}
