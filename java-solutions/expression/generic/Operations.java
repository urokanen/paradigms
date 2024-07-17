package expression.generic;

public interface Operations<T> {
    T add(T value1, T value2);
    T subtract(T value1, T value2);
    T multiply(T value1, T value2);
    T divide(T value1, T value2);
    T unary(T value);
    T cast(int value);
    T min(T value1, T value2);
    T max(T value1, T value2);
    T count(T value1);
}
