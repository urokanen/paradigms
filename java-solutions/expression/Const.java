package expression;

import java.util.List;
import java.util.Objects;

public class Const implements ExpressionWithType, ListExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return value;
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
    public String toMiniString() {
        return String.valueOf(value);
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public int hashCode() {
        return Objects.hash(value, getClass());
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Const newConst = (Const) object;
        return Objects.equals(this.value, newConst.value);
    }
}
