package queue;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
// Let: shift(k): forall i=1..k: a'[i] = a[i + 1]
public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    // Pre: element != null
    // Post: n' = n + 1 && a[n'] = element && immutable(n)
    //public void enqueue(Object element) {
    //    Objects.requireNonNull(element);

    //    enqueueImpl(element);
    //}

    public LinkedQueue getQueue() {
        return new LinkedQueue();
    }

    protected void enqueueImpl(Object element) {
        if (isEmpty()) {
            head = new Node(element);
            tail = head;
        } else {
            tail.setNext(new Node(element));
            tail = tail.getNext();
        }
    }

    // Pre: n > 0
    // Post: R = a[0] && n' = n && immutable(n)
    //public Object element() {
    //    assert size > 0;

    //    return head.value;
    //}

    protected Object elementImpl() {
        return head.value;
    }

    // Pre: n > 0
    // Post: R = a[0] && n' = n - 1 && shift(n')
    //public Object dequeue() {
    //    assert size > 0;

    //    return dequeueImpl();
    //}

    protected Object dequeueImpl() {
        Object ans = head.value;
        head = head.getNext();
        return ans;
    }

    // Pre: true
    // Post: R = n && n' = n && immutable(n)
    //public int size() {
    //    return size;
    //}

    // Pre: true
    // Post: R = (n = 0) && n' = n && immutable(n)
    //public boolean isEmpty() {
    //    return size == 0;
    //}

    // Pre: true
    // Post: n' = 0
    //public void clear() {
    //    size = 0;
    //    head = null;
    //    tail = null;
    //}

    protected void clearImpl() {
        head = null;
        tail = null;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value) {
            assert value != null;

            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
