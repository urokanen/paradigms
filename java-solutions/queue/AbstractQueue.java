package queue;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        assert element != null;

        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size > 0;

        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        assert size > 0;

        size--;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public abstract Queue getQueue();

    public Queue flatMap(Function<Object, List<Object>> function) {
        Queue result = getQueue();

        for (int i = 0; i < size(); i++) {
            Object object = dequeue();
            for (final Object element : function.apply(object)) {
                result.enqueue(element);
            }
            enqueue(object);
        }
        return result;
    }

    public Object reduce(Object init, BinaryOperator<Object> op) {
        Object ans = init;
        for (int i = 0; i < size(); i++) {
            Object object = dequeue();
            ans = op.apply(ans, object);
            enqueue(object);
        }
        return ans;
    }

    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();
}
