package expression.exceptions;

import expression.*;

public class CheckedMultiply extends Operator {
    public CheckedMultiply(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    protected int calculate(int value1, int value2) {
        if ((value1 > 0 && value2 > 0 && value1 > Integer.MAX_VALUE / value2) ||
                (value1 < 0 && value2 < 0 && value1 < Integer.MAX_VALUE / value2) ||
                (value1 < 0 && value2 > 0 && value1 < Integer.MIN_VALUE / value2) ||
                (value1 > 0 && value2 < 0 && value2 < Integer.MIN_VALUE / value1)) {
            throw new OverflowException("multiply");
        }
        return value1 * value2;
    }

    @Override
    protected String convert() {
        return " * ";
    }
}
