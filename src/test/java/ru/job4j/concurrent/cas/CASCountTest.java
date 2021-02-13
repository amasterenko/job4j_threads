package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void whenTwoThreadsIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread counter1 = new Thread(new ThreadCounter(count, 33));
        Thread counter2 = new Thread(new ThreadCounter(count, 67));
        counter1.start();
        counter2.start();
        counter1.join();
        counter2.join();
        assertThat(count.get(), is(100));
    }

    class ThreadCounter implements Runnable {
        private CASCount count;
        private int repeat;

        public ThreadCounter(CASCount count, int repeat) {
            this.count = count;
            this.repeat = repeat;
        }

        @Override
        public void run() {
            for (int i = 0; i < repeat; i++) {
                count.increment();
            }
        }
    }

}