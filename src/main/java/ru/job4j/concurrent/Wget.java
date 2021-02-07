package ru.job4j.concurrent;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String file;
    private final int speed;

    public Wget(String file, int speed) {
        this.file = file;
        this.speed = speed;
    }

    @Override
    public void run() {
        String[] splittedPath = file.split("/");
        String outputFile = "data\\" + splittedPath[splittedPath.length - 1];
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timeStamp = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long now = System.currentTimeMillis();
                long targetTime = bytesRead * 1000 / speed;
                long spentTime = now - timeStamp;
                if (spentTime < targetTime) {
                    Thread.sleep(targetTime - spentTime);
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                timeStamp = System.currentTimeMillis();
            }
            System.out.println("Done.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
