package expression.generic;

public class DoubleOperations implements Operations<Double>{
    @Override
    public Double add(Double element1, Double element2) {
        return element1 + element2;
    }

    @Override
    public Double subtract(Double element1, Double element2) {
        return element1 - element2;
    }

    @Override
    public Double multiply(Double element1, Double element2) {
        return element1 * element2;
    }

    @Override
    public Double divide(Double element1, Double element2) {
        return element1 / element2;
    }

    @Override
    public Double unary(Double element) {
        return -element;
    }

    @Override
    public Double cast(int element) {
        return (double) element;
    }

    @Override
    public Double min(Double element1, Double element2) {
        return Math.min(element1, element2);
    }

    @Override
    public Double max(Double element1, Double element2) {
        return Math.max(element1, element2);
    }

    @Override
    public Double count(Double element) {
        return (double) Long.bitCount(Double.doubleToLongBits(element));
    }
}
