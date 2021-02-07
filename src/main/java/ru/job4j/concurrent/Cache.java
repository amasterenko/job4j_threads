package ru.job4j.concurrent;

public final class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(Cache::instOf);
        Thread thread2 = new Thread(Cache::instOf);
        thread1.start();
        thread2.start();
    }
}
