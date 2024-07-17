package search;

public class BinarySearch {
    // Pre: 0 <= ans && ans < arr.length && arr sorted (decreasing) && arr[ans] <= x
    public static int binaryIterative(int x, int[] arr) {
        int l = -1;
        int r = arr.length - 1;
        // (r' - l') < (r - l)
        while (l + 1 < r) {
            // r - l > 1 => (l < (l + r) / 2 && (l + r) / 2 < r)
            int m = (l + r) / 2;
            // inv: (l == -1 || arr[l] >= arr[m]) && arr[m] >= arr[r]
            if (arr[m] > x) {
                l = m;
                // l' = m && m > l => (r' - l') < (r - l)
                // inv: arr[l'] >= arr[m] && arr[m] >= arr[r]
            } else {
                r = m;
                // r' = m && r > m => (r' - l') < (r - l)
                // inv: (l == -1 || arr[l] >= arr[m]) && arr[m] >= arr[r']
            }
            // (r' - l') < (r - l)
            // since the difference is finite and constantly decreasing =>
            // the exit condition will be fulfilled
        }
        // -1 <= l && l < r && r < arr.length && r = ans => (0 <= ans && ans < arr.length)
        return r;
    }
    // Post: 0 <= ans && ans < arr.length && arr sorted (decreasing) && arr[ans] <= x

    // Pre: r < arr.length && l >= -1 && l < r && arr sorted (decreasing) && arr[r] <= x && (r = ans || (r' - l') < (r - l))
    public static int binaryRecursive(int x, int l, int r, int[] arr) {
        if (l + 1 == r) {
            // => r = ans
            return r;
        }
        // l - r > 1 => (l < (l + r) / 2 && (l + r) / 2 < r)
        int m = (l + r) / 2;
        // inv: (l == -1 || arr[l] >= arr[m]) && arr[m] >= arr[r]
        if (arr[m] > x) {
            return binaryRecursive(x, m, r, arr);
            // l' = m && m > l => (r' - l') < (r - l)
            // inv: arr[l'] >= arr[m] && arr[m] >= arr[r]
        } else {
            return binaryRecursive(x, l, m, arr);
            // r' = m && r > m => (r' - l') < (r - l)
            // inv: (l == -1 || arr[l] >= arr[m]) && arr[m] >= arr[r']
        }
        // inv: (l' == -1 || arr[l'] >= arr[m]) && arr[m] >= arr[r']
        // (r' - l') < (r - l)
        // since the difference is finite and constantly decreasing =>
        // the exit condition will be fulfilled
    }
    // Post: r < arr.length && l >= -1 && l < r && arr sorted (decreasing) && arr[r] <= x && (r = ans || (r' - l') < (r - l))

    // Pre: x - integer && arr sorted (decreasing)
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // x - integer
        int[] arr = new int[args.length];
        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.parseInt(args[i]);
        }
        // arr sorted (decreasing)
        // adding the -inf element to the end
        arr[args.length - 1] = Integer.MIN_VALUE;
        // arr sorted
        System.out.println(binaryIterative(x, arr));
        //System.out.println(binaryRecursive(x, -1, arr.length - 1, arr));
    }
}
