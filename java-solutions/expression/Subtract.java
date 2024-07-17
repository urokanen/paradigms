package expression;

public class Subtract extends Operator {
    public Subtract(ExpressionWithType a, ExpressionWithType b) {
        super(a, b);
    }

    @Override
    public int type() {
        return 2;
    }

    @Override
    protected int calculate(int value1, int value2) {
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
