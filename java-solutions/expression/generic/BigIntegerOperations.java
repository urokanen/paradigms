package expression.generic;

import java.math.BigInteger;
import java.util.Objects;

public class BigIntegerOperations implements Operations<BigInteger> {
    @Override
    public BigInteger add(BigInteger element1, BigInteger element2) {
        return element1.add(element2);
    }

    @Override
    public BigInteger subtract(BigInteger element1, BigInteger element2) {
        return element1.subtract(element2);
    }

    @Override
    public BigInteger multiply(BigInteger element1, BigInteger element2) {
        return element1.multiply(element2);
    }

    @Override
    public BigInteger divide(BigInteger element1, BigInteger element2) {
        if (Objects.equals(element2, BigInteger.ZERO)) {
            //System.err.println("divide by zero!");
            return null;
        }
        return element1.divide(element2);
    }

    @Override
    public BigInteger unary(BigInteger element) {
        return element.negate();
    }

    @Override
    public BigInteger cast(int element) {
        return BigInteger.valueOf(element);
    }

    @Override
    public BigInteger min(BigInteger element1, BigInteger element2) {
        return element1.compareTo(element2) < 0 ? element1 : element2;
    }

    @Override
    public BigInteger max(BigInteger element1, BigInteger element2) {
        return element1.compareTo(element2) > 0 ? element1 : element2;
    }

    @Override
    public BigInteger count(BigInteger element) {
        return BigInteger.valueOf(element.bitCount());
    }
}
