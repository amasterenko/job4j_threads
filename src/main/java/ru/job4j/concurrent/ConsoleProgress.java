package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(count++);
            try {
                Thread.sleep(1000);
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
        /*LinkedList<Character> symbols = new LinkedList<>(Arrays.asList('-', '\\', '|', '/'));
        while (!Thread.currentThread().isInterrupted()) {
            char smb = symbols.poll();
            System.out.print("\rLoading..." + smb);
            symbols.add(smb);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }*/
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        while (true) {
            System.out.println(progress.getState());
        try {
                Thread.sleep(500);
            progress.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*if (progress.getState() == Thread.State.RUNNABLE) {
                progress.interrupt();
            }*/
        }
    }
}
