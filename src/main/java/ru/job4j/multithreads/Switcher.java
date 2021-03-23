package ru.job4j.multithreads;

/**
 * The class demonstrates the work of MasterSlaveBarrier class.
 * The master thread goes first, the slave thread goes second.
 *
 * @author AndrewMs
 * @version 1.0
 */
public class Switcher {
    public static void main(String[] args) {
        MasterSlaveBarrier barrier = new MasterSlaveBarrier();

        Thread first = new Thread(
                () -> {
                    while (true) {
                        try {
                            barrier.tryMaster();
                            System.out.println("Thread A");
                            barrier.doneMaster();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        try {
                            barrier.trySlave();
                            System.out.println("Thread B");
                            barrier.doneSlave();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        second.start();
    }
}