package queue;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
// Let: shift(k): forall i=1..k: a'[i] = a[i + 1]
public interface Queue {
    // Pre: element != null
    // Post: n' = n + 1 && a[n'] = element && immutable(n)
    void enqueue(Object element);

    // Pre: n > 0
    // Post: R = a[0] && n' = n && immutable(n)
    Object element();

    // Pre: n > 0
    // Post: R = a[0] && n' = n - 1 && shift(n')
    Object dequeue();

    // Pre: true
    // Post: R = n && n' = n && immutable(n)
    int size();

    // Pre: true
    // Post: R = (n = 0) && n' = n && immutable(n)
    boolean isEmpty();

    // Pre: true
    // Post: R = b[1..n] && n' = n && immutable(n) && R = b[1]..b[m] && forall i..n List ci = function(i) &&
    // m = c1.size + .. + cn.size && forall j=1..ci.size b[sum h=1..i-1 ch.size + j] = ci[j]
    Queue flatMap(Function<Object, List<Object>> function);

    // Pre: true
    // Post: n' = 0
    void clear();

    // Pre: op != null
    // Post: n' = n && immutable(n) && ti = t(ti-1, a[i]) for i=2..n && t1 = t(init, a[1]) && R = tn
    Object reduce(Object init, BinaryOperator<Object> op);
}
