package expression.generic;

public class UncheckedIntegerOperations implements Operations<Integer> {
    @Override
    public Integer add(Integer element1, Integer element2) {
        return element1 + element2;
    }

    @Override
    public Integer subtract(Integer element1, Integer element2) {
        return element1 - element2;
    }

    @Override
    public Integer multiply(Integer element1, Integer element2) {
        return element1 * element2;
    }

    @Override
    public Integer divide(Integer element1, Integer element2) {
        if (element2 == 0) {
            return null;
        }
        return element1 / element2;
    }

    @Override
    public Integer unary(Integer element) {
        return -1 * element;
    }

    @Override
    public Integer cast(int element) {
        return element;
    }

    @Override
    public Integer min(Integer element1, Integer element2) {
        return Math.min(element1, element2);
    }

    @Override
    public Integer max(Integer element1, Integer element2) {
        return Math.max(element1, element2);
    }

    @Override
    public Integer count(Integer element) {
        return Integer.bitCount(element);
    }
}
