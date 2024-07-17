package expression.generic;

public class SaturatedOperations implements Operations<Integer> {
    @Override
    public Integer add(Integer element1, Integer element2) {
        if (Check.checkAddMax(element1, element2)) {
            return Integer.MAX_VALUE;
        } else if (Check.checkAddMin(element1, element2)) {
            return Integer.MIN_VALUE;
        }
        return element1 + element2;
    }

    @Override
    public Integer subtract(Integer element1, Integer element2) {
        if (Check.checkSubMax(element1, element2)) {
            return Integer.MAX_VALUE;
        } else if (Check.checkSubMin(element1, element2)) {
            return Integer.MIN_VALUE;
        }
        return element1 - element2;
    }

    @Override
    public Integer multiply(Integer element1, Integer element2) {
        if (Check.checkMulMax(element1, element2)) {
            return Integer.MAX_VALUE;
        } else if (Check.checkMulMin(element1, element2)) {
            return Integer.MIN_VALUE;
        }
        return element1 * element2;
    }

    @Override
    public Integer divide(Integer element1, Integer element2) {
        if (element2 == 0) {
            return null;
        } else if (Check.checkDivideMax(element1, element2)) {
            return Integer.MAX_VALUE;
        }
        return element1 / element2;
    }

    @Override
    public Integer unary(Integer element) {
        if (element == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
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
