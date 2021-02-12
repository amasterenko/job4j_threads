package ru.job4j.concurrent.buffer;

import ru.job4j.concurrent.wait.SimpleBlockingQueue;

public class ParallelSearchPoisonPill {
    static int stop = -1;

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            int value = queue.poll();
                            if (value == stop) {
                                break;
                            }
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
                    queue.offer(stop);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        ).start();
    }
}
