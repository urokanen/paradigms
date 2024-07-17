package expression.exceptions;

import expression.*;

import java.util.List;

public class CheckedNegate implements ExpressionWithType {
    private final ExpressionWithType expression;

    public CheckedNegate(ExpressionWithType expression) {
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
        if (expression.evaluate(x) == Integer.MIN_VALUE) {
            throw new OverflowException("unary minus");
        }
        return -1 * expression.evaluate(x);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        if (expression.evaluate(variables) == Integer.MIN_VALUE) {
            throw new OverflowException("unary minus");
        }
        return -1 * expression.evaluate(variables);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (expression.evaluate(x, y, z) == Integer.MIN_VALUE) {
            throw new OverflowException("unary minus");
        }
        return -1 * expression.evaluate(x, y, z);
    }

    @Override
    public String toMiniString() {
        if (expression instanceof Operator) {
            return "-(" + expression.toMiniString() + ')';
        }
        return "- " + expression.toMiniString();
    }
}
