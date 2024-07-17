package expression.exceptions;

import expression.*;

public class CheckedMin extends Operator {
    public CheckedMin(ExpressionWithType a, ExpressionWithType b) {
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
        return Math.min(value1, value2);
    }

    @Override
    protected String convert() {
        return " min ";
    }
}
