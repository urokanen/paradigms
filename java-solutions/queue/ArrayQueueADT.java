package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
// Let: shift(k): forall i=1..k: a'[i] = a[i + 1]
public class ArrayQueueADT {
    private int pos;
    private int count = 10;
    private int len = 10;
    private Object[] elements = new Object[10];

    public ArrayQueueADT() {
    }

    // Pre: true
    // Post: R.n = 0
    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        return queue;
    }

    // Pre: queue != null && element != null
    // Post: n' = n + 1 && a[n'] = element && immutable(n)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue);
        queue.elements[queue.pos] = element;
        queue.count--;
        queue.pos = calcVal(queue, queue.pos + 1);
    }

    // Pre: queue != null && element != null
    // Post: n' = n + 1 && a[0] = element && forall i=1..n a'[i + 1] = a[i]
    public static void push(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue);
        queue.elements[calcVal(queue, queue.pos + queue.count - 1)] = element;
        queue.count--;
    }

    // Pre: queue != null && n > 0
    // Post: R = a[n] && n' = n - 1 && immutable(n')
    public static Object remove(ArrayQueueADT queue) {
        assert queue.count < queue.len;

        queue.count++;
        queue.pos = calcVal(queue, queue.pos + queue.len - 1);
        return queue.elements[queue.pos];
    }

    // Pre: queue != null && n > 0
    // Post: R = a[n] && n' = n && immutable(n)
    public static Object peek(ArrayQueueADT queue) {
        assert queue.count < queue.len;

        return queue.elements[calcVal(queue, queue.pos + queue.len - 1)];
    }

    // Pre: queue != null
    // Post: n' = n && immutable(n)
    private static void ensureCapacity(ArrayQueueADT queue) {
        if (queue.count == 0) {
            Object[] newElements = new Object[queue.len * 2];
            if (queue.pos >= queue.len - queue.count) {
                System.arraycopy(queue.elements, queue.pos - queue.len + queue.count,
                        newElements, 0, queue.len - queue.count);
            } else {
                System.arraycopy(queue.elements, queue.len - (queue.len - queue.count - queue.pos),
                        newElements, 0, queue.len - queue.count - queue.pos);
                System.arraycopy(queue.elements, 0, newElements,
                        queue.len - queue.count - queue.pos, queue.pos);
            }
            queue.pos = queue.len;
            queue.count = queue.len;
            queue.len *= 2;
            queue.elements = newElements;
        }
    }

    // Pre: queue != null && n > 0
    // Post: R = a[0] && n' = n && immutable(n)
    public static Object element(ArrayQueueADT queue) {
        assert queue.count < queue.len;

        return queue.elements[calcVal(queue, queue.pos + queue.count)];
    }

    // Pre: queue != null && n > 0
    // Post: R = a[0] && n' = n - 1 && shift(n')
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.count < queue.len;

        return queue.elements[calcVal(queue, queue.pos + queue.count++)];
    }

    // Pre: queue != null
    // Post: R = n && n' = n && immutable(n)
    public static int size(ArrayQueueADT queue) {
        return queue.len - queue.count;
    }

    // Pre: queue != null
    // Post: R = (n = 0) && n' = n && immutable(n)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.len - queue.count == 0;
    }

    // Pre: squeue != null && 0 <= value && value < 2 * len
    // Post: R = value % len
    private static int calcVal(ArrayQueueADT queue, int value) {
        if (value < queue.len) {
            return value;
        }
        return value - queue.len;
    }

    // Pre: queue != null
    // Post: n' = n && immutable(n) && (ans == -1 || (t(a[ans]) && ans = min)
    public static int indexIf(ArrayQueueADT queue, Predicate<Object> t) {
        if (queue.isEmpty(queue)) {
            return -1;
        }

        for (int i = 0; i < queue.len - queue.count; i++) {
            if (t.test(queue.elements[calcVal(queue, queue.pos + queue.count + i)])) {
                return i;
            }
        }
        return -1;
    }

    // Pre: queue != null
    // Post: n' = n && immutable(n) && (ans == -1 || (t(a[ans]) && ans = max)
    public static int lastIndexIf(ArrayQueueADT queue, Predicate<Object> t) {
        if (queue.isEmpty(queue)) {
            return -1;
        }

        for (int i = queue.len - queue.count - 1; i >= 0; i--) {
            if (t.test(queue.elements[calcVal(queue, queue.pos + queue.count + i)])) {
                return i;
            }
        }
        return -1;
    }

    // Pre: queue != null
    // Post: n' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.count = queue.len;
    }
}
