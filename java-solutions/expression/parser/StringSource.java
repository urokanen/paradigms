package expression.parser;

public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(String data) {
        this.data = data;
    }

    @Override
    public boolean check(String test) {
        int ind = pos - 1;
        while (ind < data.length() && ind - pos + 1 < test.length() &&
                data.charAt(ind) == test.charAt(ind - pos + 1)) {
            ind++;
        }
        return ind - pos + 1 == test.length();
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public IllegalArgumentException error(final String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }
}
