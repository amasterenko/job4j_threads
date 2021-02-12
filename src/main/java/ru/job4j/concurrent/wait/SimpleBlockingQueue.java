package ru.job4j.concurrent.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int maxSize;
    private int size;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (size >= maxSize) {
            wait();
        }
        queue.offer(value);
        size++;
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (size <= 0) {
            wait();
        }
        T rsl = queue.poll();
        size--;
        notifyAll();
        return rsl;
    }

    public synchronized int size() {
        return size;
    }

}
