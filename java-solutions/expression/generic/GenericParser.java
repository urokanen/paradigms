package expression.generic;

import expression.exceptions.IncorrectSymbolException;
import expression.exceptions.UnknownVariableException;
import expression.parser.BaseParser;
import expression.parser.StringSource;

import java.util.List;

public class GenericParser<T> extends BaseParser {
    private final List<String> variables;
    private final Operations<T> operations;

    public GenericParser(Operations<T> operations) {
        this.variables = List.of("x", "y", "z");
        this.operations = operations;
    }

    public GenericExpression<T> parse(String expression) {
        setSource(new StringSource(expression));
        return parse();
    }

    private GenericExpression<T> parse() {
        return parseBinaryOperation(0);
    }

    private final static List<List<String>> parserOperations = List.of(
            List.of("min", "max"),
            List.of("+", "-"),
            List.of("/", "*")
    );

    private GenericExpression<T> getConstructor(String operation, GenericExpression<T> element1, GenericExpression<T> element2) {
        return switch (operation) {
            case "+" -> new Add<>(element1, element2, operations);
            case "-" -> new Subtract<>(element1, element2, operations);
            case "*" -> new Multiply<>(element1, element2, operations);
            case "/" -> new Divide<>(element1, element2, operations);
            case "min" -> new Min<>(element1, element2, operations);
            case "max" -> new Max<>(element1, element2, operations);
            default -> null;
        };
    }

    private GenericExpression<T> parseNext(int index) {
        return index + 1 == parserOperations.size() ? parseOther() : parseBinaryOperation(index + 1);
    }

    private GenericExpression<T> parseBinaryOperation(int index) {
        GenericExpression<T> parsed = parseNext(index);
        while (true) {
            boolean flag = false;
            skipWhitespace();
            for (String operation : parserOperations.get(index)) {
                if (checkString(operation)) {
                    parsed = getConstructor(operation, parsed, parseNext(index));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return parsed;
            }
        }
    }

    private GenericExpression<T> parseOther() {
        skipWhitespace();
        if (take('(')) {
            GenericExpression<T> result = parse();
            expect(')');
            return result;
        } else if (between('0', '9')) {
            return parseConst(1);
        }  else if (checkString("count")) {
            expect('(');
            GenericExpression<T> res = new Count<>(parse(), operations);
            expect(')');
            return res;
        } else if (Character.isJavaIdentifierStart(ch)) {
            return parseVariable();
        } else if (take('-')) {
            if (between('1', '9')) {
                return parseConst(-1);
            }
            skipWhitespace();
            return new UnaryMinus<>(parseOther(), operations);
        } else {
            throw new IncorrectSymbolException(Character.toString(ch), getPosition());
        }
    }

    private GenericExpression<T> parseVariable() {
        skipWhitespace();
        for (String element : variables) {
            if (checkString(element)) {
                return new Variable<>(element);
            }
        }
        throw new UnknownVariableException();
    }

    private GenericExpression<T> parseConst(int value) {
        StringBuilder sb = new StringBuilder();
        if (value == -1) {
            sb.append('-');
        }
        while (between('0', '9')) {
            sb.append(ch);
            take();
        }
        return new Const<>(Integer.parseInt(sb.toString()), operations);
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}
