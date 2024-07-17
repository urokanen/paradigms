package expression.exceptions;

import expression.Const;
import expression.ExpressionWithType;
import expression.ListExpression;
import expression.Variable;
import expression.parser.BaseParser;
import expression.parser.StringSource;
import expression.parser.TripleParser;

import java.util.List;
import java.util.Map;

public class ExpressionParser extends BaseParser implements TripleParser, ListParser {
    private List <String> variables;

    @Override
    public ExpressionWithType parse(String expression) {
        setSource(new StringSource(expression));
        this.variables = List.of("x", "y", "z");
        return startParse();
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) {
        setSource(new StringSource(expression));
        this.variables = variables;
        return startParse();
    }

    private ExpressionWithType startParse() {
        ExpressionWithType ans = parseMinMax();
        if (!isEnd()) {
            throw new IncorrectSymbolException(Character.toString(ch), getPosition());
        }
        return ans;
    }

    private ExpressionWithType parse() {
        return parseMinMax();
    }

    private void checkAfterMinMax() {
        if (between('0', '9') || between('a', 'z')) {
            throw new IncorrectSymbolException(Character.toString(ch), getPosition());
        }
    }

    private ExpressionWithType parseMinMax() {
        ExpressionWithType parsed = parseShift();
        while (true) {
            skipWhitespace();
            if (checkString("min")) {
                checkAfterMinMax();
                parsed = new CheckedMin(parsed, parseShift());
            } else if (checkString("max")) {
                checkAfterMinMax();
                parsed = new CheckedMax(parsed, parseShift());
            } else {
                return parsed;
            }
        }
    }

    private ExpressionWithType parseShift() {
        ExpressionWithType parsed = parseAddSubtract();
        while (true) {
            skipWhitespace();
            if (checkString("<<")) {
                parsed = new CheckedLeftShift(parsed, parseAddSubtract());
            } else if (checkString(">>>")) {
                parsed = new CheckedArithmeticShift(parsed, parseAddSubtract());
            } else if (checkString(">>")) {
                parsed = new CheckedRightShift(parsed, parseAddSubtract());
            } else {
                return parsed;
            }
        }
    }

    private ExpressionWithType parseAddSubtract() {
        ExpressionWithType parsed = parseMultiplyDivide();
        while (true) {
            skipWhitespace();
            if (take('+')) {
                parsed = new CheckedAdd(parsed, parseMultiplyDivide());
            } else if (take('-')) {
                parsed = new CheckedSubtract(parsed, parseMultiplyDivide());
            } else {
                return parsed;
            }
        }
    }

    private ExpressionWithType parseMultiplyDivide() {
        ExpressionWithType parsed = parseOther();
        while (true) {
            skipWhitespace();
            if (take('*')) {
                parsed = new CheckedMultiply(parsed, parseOther());
            } else if (take('/')) {
                parsed = new CheckedDivide(parsed, parseOther());
            } else {
                return parsed;
            }
        }
    }

    static final Map<Character, Character> map = Map.of(
            '(', ')',
            '{', '}',
            '[', ']'
    );

    private ExpressionWithType parseOther() {
        skipWhitespace();
        if (map.containsKey(ch)) {
            char c = ch;
            take();
            ExpressionWithType result = parse();
            expect(map.get(c));
            return result;
        } else if (between('0', '9')) {
            return parseConst(1);
        } else if (Character.isJavaIdentifierStart(ch)) {
            return parseVariable();
        } else if (take('-')) {
            if (between('1', '9')) {
                return parseConst(-1);
            }
            skipWhitespace();
            return new CheckedNegate(parseOther());
        }
        else {
            throw new IncorrectSymbolException(Character.toString(ch), getPosition());
        }
    }

    private ExpressionWithType parseVariable() {
        skipWhitespace();
        for (int i = 0; i < variables.size(); i++) {
            if (checkString(variables.get(i))) {
                return new Variable(i, String.valueOf(variables.get(i)));
            }
        }
        throw new UnknownVariableException();
    }

    private ExpressionWithType parseConst(int value) {
        skipWhitespace();
        StringBuilder sb = new StringBuilder();
        while (between('0', '9')) {
            sb.append(ch);
            take();
        }
        if (sb.toString().equals("2147483648")) {
            if (value == -1) {
                return new Const(Integer.MIN_VALUE);
            }
            throw new IncorrectNumberException();
        } else {
            try {
                return new Const(value * Integer.parseInt(sb.toString()));
            } catch (NumberFormatException e) {
                throw new IncorrectNumberException();
            }
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}
