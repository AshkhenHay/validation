public class BoundedBlockingBuffer<T> {

    private final Object[] buffer;
    private int put, take, count;

    public BoundedBlockingBuffer(int bound) {
        buffer = new Object[bound];
    }

    public synchronized void put(T data) {
        try {
            while (count == buffer.length) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer[put] = data;
        if (++put == buffer.length) {
            put = 0;
        }
        ++count;
        notifyAll();
    }

    public synchronized T take() {
        try {
            while (count == 0) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        T element = (T) buffer[take];
        if (++take == buffer.length) {
            take = 0;
        }
        --count;
        notifyAll();
        return element;
    }

}




//Bounded blocking buffer - a simple data structure that allows data exchange between threads.
//Principle of operation.
//The blocking buffer allows you to:
//1. Put in it the value you want to transfer. If the buffer already contains some value, then the thread waits until someone picks it up.
//2. Pick up the value put there. If the buffer is empty, then the thread waits until someone puts a value into it.
//
//Class structure
//
//Please make a class generic.