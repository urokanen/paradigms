package expression.generic;

public class Check {
    public static boolean checkAddMax(int element1, int element2) {
        return element1 > 0 && element2 > Integer.MAX_VALUE - element1;
    }

    public static boolean checkAddMin(int element1, int element2) {
        return element1 < 0 && element2 < Integer.MIN_VALUE - element1;
    }

    public static boolean checkSubMax(int element1, int element2) {
        return element1 >= 0 && element2 < 0 && element1 > Integer.MAX_VALUE + element2;
    }

    public static boolean checkSubMin(int element1, int element2) {
        return element1 < 0 && element2 > 0 && element1 < Integer.MIN_VALUE + element2;
    }

    public static boolean checkMulMax(int element1, int element2) {
        return (element1 > 0 && element2 > 0 && element1 > Integer.MAX_VALUE / element2) ||
                (element1 < 0 && element2 < 0 && element1 < Integer.MAX_VALUE / element2);
    }

    public static boolean checkMulMin(int element1, int element2) {
        return (element1 < 0 && element2 > 0 && element1 < Integer.MIN_VALUE / element2) ||
                (element1 > 0 && element2 < 0 && element2 < Integer.MIN_VALUE / element1);
    }

    public static boolean checkDivideMax(int element1, int element2) {
        return element1 == Integer.MIN_VALUE && element2 == -1;
    }
}
