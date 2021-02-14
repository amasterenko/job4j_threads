package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int queueMaxSize) {
        tasks = new SimpleBlockingQueue<>(queueMaxSize);
        int sizeOfThreadPool = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < sizeOfThreadPool; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                                System.out.println("job completed");
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(10);
        Runnable job = () -> System.out.println("job started");
        for (int i = 0; i < 10; i++) {
            pool.work(job);
        }
        Thread.sleep(500);
        pool.shutdown();
    }
}
