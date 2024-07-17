package expression.parser;

import expression.exceptions.*;

public class BaseParser {
    private static final char END = '\0';
    private CharSource source;
    protected char ch = 0xffff;

    protected void setSource(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    public int getPosition() {
        return source.getPos();
    }

    public boolean checkString(String test) {
        if (source.check(test)) {
            expect(test);
            return true;
        }
        return false;
    }

    public boolean isEnd() {
        return take() == END;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw new IncorrectBracketsException(Character.toString(expected));
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
