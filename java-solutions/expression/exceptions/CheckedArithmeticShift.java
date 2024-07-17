package expression.exceptions;

import expression.ExpressionWithType;
import expression.Operator;

public class CheckedArithmeticShift extends Operator {
    public CheckedArithmeticShift(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 3;
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
    protected int calculate(int value1, int value2) {
        return value1 >>> value2;
    }

    @Override
    protected String convert() {
        return " >>> ";
    }
}
