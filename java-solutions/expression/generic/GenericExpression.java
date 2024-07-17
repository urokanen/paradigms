package expression.generic;

public interface GenericExpression<T> {
    T evaluate(T value1, T value2, T value3);
}
