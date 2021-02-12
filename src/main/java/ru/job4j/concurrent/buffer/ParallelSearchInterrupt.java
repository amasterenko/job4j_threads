package ru.job4j.concurrent.buffer;

import ru.job4j.concurrent.wait.SimpleBlockingQueue;

public class ParallelSearchInterrupt {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            int value = queue.poll();
                            System.out.println(value);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    try {
                    for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                            Thread.sleep(500);
                    }
                    consumer.interrupt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        ).start();
    }
}
