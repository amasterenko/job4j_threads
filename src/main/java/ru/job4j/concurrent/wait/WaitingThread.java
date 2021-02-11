package ru.job4j.concurrent.wait;

public class WaitingThread implements Runnable {
    private CountBarrier barrier;

    public WaitingThread(CountBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " continued");
    }
}
