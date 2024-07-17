package search;


public class BinarySearchShift {
    // Pre: (0 <= l && l < r && r <= arr.length) && arr shifted + sorted (increasing) && (ans == final l || ans == -1)
    // && (arr[l] <= x || (arr[l] >= arr[0] && arr[0] > x)
    // shifted => there are two halves 0..k is bigger and k..n smaller
    public static int binaryRecursiveShift(int x, int l, int r, int[] arr) {
        if (l + 1 == r) {
            // l + 1 == r => final l
            if (arr[l] == x) {
                // => ans == final l
                return l;
            }
            // => ans == -1
            return -1;
            // => (ans == final l || ans == -1)
        }
        // r - l < 1 => l < (l + r) / 2 && (l + r) / 2 < r
        int m = (l + r) / 2;
        // => l < m && m < r
        // either we have arr[l] half to the left of x or we have arr[l] <= arr[x]
        if ((arr[0] <= arr[m] && (arr[m] <= x || arr[0] > x)) ||
                (arr[m] <= x && x < arr[0])) {
            // => either we have arr[m] half to the left of x or we have arr[m] <= arr[x]
            // (arr[m] <= x || (arr[m] >= arr[0] && arr[0] > x) => m = l
            return binaryRecursiveShift(x, m, r, arr);
            // => m = l'
        } else {
            // arr[0] > arr[m] or (arr[m] > x && arr[0] <= x)
            // => !either we have arr[m] half to the left of x or we have arr[m] <= arr[x]
            // => !(arr[m] <= x || (arr[m] >= arr[0] && arr[0] > x) => m = r
            return binaryRecursiveShift(x, l, m, arr);
            // => m = r'
        }
    }
    // Post: (arr[l] == x || ans == -1)

    // Pre: (ans == -1 || (0 <= ans && ans < arr.length && arr[ans] == x)) && arr shifted + sorted (increasing)
    // shifted => there are two halves 0..k is bigger and k..n smaller
    public static int binaryIterativeShift(int x, int[] arr) {
        int l = 0;
        int r = arr.length;
        // (arr[l] <= x || (arr[l] >= arr[0] && arr[0] > x)
        // l < r
        while (l + 1 < r) {
            int m = (l + r) / 2;
            // either we have arr[l] half to the left of x or we have arr[l] <= arr[x]
            if ((arr[0] <= arr[m] && (arr[m] <= x || arr[0] > x)) ||
                    (arr[m] <= x && x < arr[0])) {
                // => either we have arr[m] half to the left of x or we have arr[m] <= arr[x]
                // (arr[m] <= x || (arr[m] >= arr[0] && arr[0] > x) => m = l
                l = m;
                // => m = l'
            } else {
                // => !either we have arr[m] half to the left of x or we have arr[m] <= arr[x]
                // => !(arr[m] <= x || (arr[m] >= arr[0] && arr[0] > x) => m = r
                r = m;
                // => x = r'
            }
        }
        // l + 1 == r => final l
        if (arr[l] == x) {
            return l;
            // => ans == final l
        }
        // => ans == -1
        return -1;
        // => (ans == final l || ans == -1)
    }
    // Post: (ans == -1 || (0 <= ans && ans < arr.length && arr[ans] == x)) && arr shifted + sorted (increasing)

    // Pre: x - integer && arr sorted + shifted (increasing)
    // shifted => there are two halves 0..k is bigger and k..n smaller
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // x - integer
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.parseInt(args[i]);
        }
        // arr - sorted (increasing) + shifted
        // shifted => there are two halves 0..k is bigger and k..n smaller
        System.out.println(binaryIterativeShift(x, arr));
        // 0 == l && r == arr.length => (0 <= l && l < r && r <= arr.length)
        //System.out.println(binaryRecursiveShift(x, 0, arr.length, arr));
    }
}
