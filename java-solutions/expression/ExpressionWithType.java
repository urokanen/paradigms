package expression;

public interface ExpressionWithType extends Expression, TripleExpression, ListExpression {
    int type();
    int priority();
    boolean isRight();
}
