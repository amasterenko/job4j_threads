package ru.job4j.concurrent.wait;

public class CountingThread implements Runnable {
    private CountBarrier barrier;
    private int count;

    public CountingThread(CountBarrier barrier, int count) {
        this.barrier = barrier;
        this.count = count;
    }

    /**
     * Calls barrier's count() method in a loop with a delay of 1 second
     */

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        for (int i = 1; i <= count; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("count:" + i);
            barrier.count();
        }
    }
}
