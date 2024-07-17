package expression;

import java.util.List;

public class UnaryMinus implements ExpressionWithType {
    private final ExpressionWithType expression;

    public UnaryMinus(ExpressionWithType expression) {
        this.expression = expression;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return -1 * expression.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -1 * expression.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return -1 * expression.evaluate(variables);
    }

    @Override
    public String toMiniString() {
        if (expression instanceof Operator) {
            return "-(" + expression.toMiniString() + ')';
        }
        return "- " + expression.toMiniString();
    }
}
