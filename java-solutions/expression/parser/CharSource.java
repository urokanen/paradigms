package expression.parser;

public interface CharSource {
    boolean hasNext();
    char next();
    boolean check(String test);
    int getPos();
    IllegalArgumentException error(String message);
}
