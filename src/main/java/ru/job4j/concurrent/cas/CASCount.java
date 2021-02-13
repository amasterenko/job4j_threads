package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer temp;
        int newCount;
        do {
            temp = count.get();
            newCount = temp + 1;
        } while (!count.compareAndSet(temp, newCount));
    }

    public int get() {
        return count.get();
    }
}