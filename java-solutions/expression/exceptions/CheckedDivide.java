package expression.exceptions;

import expression.*;

public class CheckedDivide extends Operator {
    public CheckedDivide(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    protected int calculate(int value1, int value2) {
        if (value2 == 0) {
            throw new ZeroDivideException();
        } else if (value1 == Integer.MIN_VALUE && value2 == -1) {
            throw new OverflowException("divide");
        }
        return value1 / value2;
    }

    @Override
    protected String convert() {
        return " / ";
    }
}
