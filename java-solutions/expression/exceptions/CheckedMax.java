package expression.exceptions;

import expression.*;

public class CheckedMax extends Operator {
    public CheckedMax(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 4;
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
        return Math.max(value1, value2);
    }

    @Override
    protected String convert() {
        return " max ";
    }
}
