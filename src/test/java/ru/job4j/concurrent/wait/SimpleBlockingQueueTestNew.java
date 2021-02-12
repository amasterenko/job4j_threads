package ru.job4j.concurrent.wait;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTestNew {

    @Test
    public void whenTwoProducersOfferThenOneConsumerPools() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer1 = new Thread(new Producer(queue, 1, 3));
        Thread producer2 = new Thread(new Producer(queue, 4, 8));
        Thread consumer = new Thread(new Consumer(queue, buffer));
        producer1.start();
        producer2.start();
        consumer.start();
        consumer.interrupt();
        producer1.join();
        producer2.join();
        consumer.join();
        assertThat(buffer, is(List.of(1, 2, 3, 4, 5, 6, 7, 8)));
        assertThat(queue.size(), is(0));
    }

    class Consumer implements Runnable {
        private final SimpleBlockingQueue<Integer> queue;
        private final CopyOnWriteArrayList<Integer> buffer;

        public Consumer(SimpleBlockingQueue<Integer> queue, CopyOnWriteArrayList<Integer> buffer) {
            this.queue = queue;
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (queue.size() != 0 || !Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class Producer implements Runnable {
        private final SimpleBlockingQueue<Integer> queue;
        private final int startValue;
        private final int endValue;

        public Producer(SimpleBlockingQueue<Integer> queue, int startValue, int endValue) {
            this.queue = queue;
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            for (int i = startValue; i <= endValue; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}