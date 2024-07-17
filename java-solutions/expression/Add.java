package expression;

public class Add extends Operator {
    public Add(ExpressionWithType a, ExpressionWithType b) {
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
        return value1 + value2;
    }

    @Override
    protected String convert() {
        return " + ";
    }
}
