package ru.job4j.concurrent.wait;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenQueueSize1Offer1Pool1() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(new Producer(queue, 1));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.size(), is(0));
    }

    @Test
    public void whenQueueSize1Offer2Pool1() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer1 = new Thread(new Producer(queue, 1));
        Thread producer2 = new Thread(new Producer(queue, 2));
        Thread consumer = new Thread(new Consumer(queue));
        producer1.start();
        producer2.start();
        consumer.start();
        producer1.join();
        producer2.join();
        consumer.join();
        assertThat(queue.size(), is(1));
    }

    class Consumer implements Runnable {
        private final SimpleBlockingQueue<Integer> queue;

        public Consumer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Producer implements Runnable {
        private final SimpleBlockingQueue<Integer> queue;
        private final int offeredValue;

        public Producer(SimpleBlockingQueue<Integer> queue, int offeredValue) {
            this.queue = queue;
            this.offeredValue = offeredValue;
        }

        @Override
        public void run() {
            try {
                queue.offer(offeredValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}