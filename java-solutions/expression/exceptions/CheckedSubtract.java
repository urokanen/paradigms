package expression.exceptions;

import expression.*;

public class CheckedSubtract extends Operator {
    public CheckedSubtract(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 2;
    }

    @Override
    protected int calculate(int value1, int value2) {
        if ((value1 >= 0 && value2 < 0 && value1 > Integer.MAX_VALUE + value2) ||
                (value1 < 0 && value2 > 0 && value1 < Integer.MIN_VALUE + value2)) {
            throw new OverflowException("subtract");
        }
        return value1 - value2;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    protected String convert() {
        return " - ";
    }
}
