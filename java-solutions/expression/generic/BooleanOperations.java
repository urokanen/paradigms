package expression.generic;

public class BooleanOperations implements Operations<Boolean> {
    @Override
    public Boolean add(Boolean element1, Boolean element2) {
        return element1 || element2;
    }

    @Override
    public Boolean subtract(Boolean element1, Boolean element2) {
        return element1 ^ element2;
    }

    @Override
    public Boolean multiply(Boolean element1, Boolean element2) {
        return element1 && element2;
    }

    @Override
    public Boolean divide(Boolean element1, Boolean element2) {
        if (!element2) {
            return null;
        }
        return element1;
    }

    @Override
    public Boolean unary(Boolean element) {
        return element;
    }

    @Override
    public Boolean cast(int element) {
        return element != 0;
    }

    @Override
    public Boolean max(Boolean element1, Boolean element2) {
        return element1 || element2;
    }

    @Override
    public Boolean min(Boolean element1, Boolean element2) {
        return element1 && element2;
    }

    @Override
    public Boolean count(Boolean element) {
        return element;
    }
}
