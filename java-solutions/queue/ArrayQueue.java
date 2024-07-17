package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
// Let: shift(k): forall i=1..k: a'[i] = a[i + 1]
public class ArrayQueue extends AbstractQueue {
    private int pos = 0;
    private int count = 5;
    private int len = 5;
    private Object[] elements = new Object[5];

    public ArrayQueue getQueue() {
        return new ArrayQueue();
    }

    // Pre: element != null
    // Post: n' = n + 1 && a[n'] = element && immutable(n)
    //public void enqueue(Object element) {
    //    Objects.requireNonNull(element);

    //    enqueueImpl(element);
    //}

    protected void enqueueImpl(Object element) {
        ensureCapacity();
        elements[pos] = element;
        count--;
        pos = calcVal(pos + 1);
    }

    // Pre: element != null
    // Post: n' = n + 1 && a[0] = element && forall i=1..n a'[i + 1] = a[i]
    public void push(Object element) {
        Objects.requireNonNull(element);

        size += 1;
        ensureCapacity();
        elements[calcVal(pos + count - 1)] = element;
        count--;
    }

    // Pre: n > 0
    // Post: R = a[n] && n' = n && immutable(n)
    public Object peek() {
        assert count < len;

        return elements[calcVal(pos + len - 1)];
    }

    // Pre: n > 0
    // Post: R = a[n] && n' = n - 1 && immutable(n')
    public Object remove() {
        assert count < len;

        size -= 1;
        count++;
        pos = calcVal(pos + len - 1);
        return elements[pos];
    }

    // Pre: true
    // Post: n' = n && immutable(n)
    private void ensureCapacity() {
        if (count == 0) {
            Object[] newElements = new Object[len * 2];
            if (pos >= len - count) {
                System.arraycopy(elements, pos - len + count,
                        newElements, 0, len - count);
            } else {
                System.arraycopy(elements, len - (len - count - pos),
                        newElements, 0, len - count - pos);
                System.arraycopy(elements, 0, newElements, len - count - pos, pos);
            }
            pos = len;
            count = len;
            len *= 2;
            elements = newElements;
        }
    }

    // Pre: n > 0
    // Post: R = a[0] && n' = n && immutable(n)
    //public Object element() {
    //    assert count < len;

    //    return elementImpl();
    //}

    protected Object elementImpl() {
        return elements[calcVal(pos + count)];
    }

    // Pre: n > 0
    // Post: R = a[0] && n' = n - 1 && shift(n')
    //public Object dequeue() {
    //    assert count < len;

    //    return dequeueImpl();
    //}

    protected Object dequeueImpl() {
        Object ans = elements[calcVal(pos + count)];
        count++;
        return ans;
    }

    // Pre: true
    // Post: R = n && n' = n && immutable(n)
    //public int size() {
    //    return len - count;
    //}

    // Pre: true
    // Post: R = (n = 0) && n' = n && immutable(n)
    //public boolean isEmpty() {
    //    return len - count == 0;
    //}

    // Pre: 0 <= value && value < 2 * len
    // Post: R = value % len
    private int calcVal(int value) {
        if (value < len) {
            return value;
        }
        return value - len;
    }

    // Pre: true
    // Post: n' = n && immutable(n) && (ans == -1 || (t(a[ans]) && ans = min)
    public int indexIf(Predicate<Object> t) {
        if (isEmpty()) {
            return -1;
        }

        for (int i = 0; i < len - count; i++) {
            if (t.test(elements[calcVal(pos + count + i)])) {
                return i;
            }
        }
        return -1;
    }

    // Pre: true
    // Post: n' = n && immutable(n) && (ans == -1 || (t(a[ans]) && ans = max)
    public int lastIndexIf(Predicate<Object> t) {
        if (isEmpty()) {
            return -1;
        }

        for (int i = len - count - 1; i >= 0; i--) {
            if (t.test(elements[calcVal(pos + count + i)])) {
                return i;
            }
        }
        return -1;
    }

    // Pre: true
    // Post: n' = 0
    //public void clear() {
    //    count = len;
    //}

    protected void clearImpl() {
        count = len;
    }
}
