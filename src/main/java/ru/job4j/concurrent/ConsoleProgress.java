package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.LinkedList;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        LinkedList<Character> symbols = new LinkedList<>(Arrays.asList('-', '\\', '|', '/'));
        while (!Thread.currentThread().isInterrupted()) {
            char symbol = symbols.pop();
            System.out.print("\rLoading..." + symbol);
            symbols.add(symbol);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
