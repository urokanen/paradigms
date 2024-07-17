package expression.generic;

import expression.exceptions.ExpressionException;

public class GenericTabulator implements Tabulator {
    private static Operations<?> creator(String mode) {
        return switch (mode) {
            case "i" -> new IntegerOperations();
            case "d" -> new DoubleOperations();
            case "bi" -> new BigIntegerOperations();
            case "u" -> new UncheckedIntegerOperations();
            case "b" -> new ByteOperations();
            case "sat" -> new SaturatedOperations();
            default -> null;
        };
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Operations<?> operations = creator(mode);
        if (operations != null) {
            return tabulate(operations, expression, x1, x2, y1, y2, z1, z2);
        } else {
            throw new Exception("Unexpected mode");
        }
    }

    private static <T> Object[][][] tabulate(Operations<T> operations, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        GenericParser<T> parser = new GenericParser<>(operations);
        GenericExpression<T> parsed = parser.parse(expression);
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        ans[x - x1][y - y1][z - z1] = parsed.evaluate(operations.cast(x), operations.cast(y), operations.cast(z));
                    } catch (ExpressionException exception) {
                        //System.err.println("Error during parsing!");
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        String mode = args[0].substring(1);
        String expression = args[1];
        Operations<?> operations = creator(mode);
        Object[][][] ans = tabulate(operations, expression, -2, 2, -2, 2, -2, 2);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                for (int z = 0; z < 5; z++) {
                    System.out.println(ans[x][y][z]);
                }
            }
        }
    }
}
