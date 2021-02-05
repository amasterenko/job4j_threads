package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) throws InterruptedException {
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        //Thread.sleep(10);
        System.out.println(Thread.currentThread().getName());

    }
}
