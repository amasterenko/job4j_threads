package ru.job4j.concurrent.wait;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (monitor) {
            while (count != total) {
                monitor.wait();
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread waitingThread1 = new Thread(new WaitingThread(barrier), "waitingThread1");
        Thread waitingThread2 = new Thread(new WaitingThread(barrier), "waitingThread2");
        Thread waitingThread3 = new Thread(new WaitingThread(barrier), "waitingThread3");
        Thread countingThread = new Thread(new CountingThread(barrier, 5), "countingThread");

        waitingThread1.start();
        waitingThread2.start();
        waitingThread3.start();
        countingThread.start();
    }
}
