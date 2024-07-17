package expression.exceptions;

import expression.*;

public class CheckedAdd extends Operator {
    public CheckedAdd(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 2;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    protected int calculate(int value1, int value2) {
        if ((value1 > 0 && value2 > Integer.MAX_VALUE - value1) ||
                (value1 < 0 && value2 < Integer.MIN_VALUE - value1)) {
            throw new OverflowException("add");
        }
        return value1 + value2;
    }

    @Override
    protected String convert() {
        return " + ";
    }
}
