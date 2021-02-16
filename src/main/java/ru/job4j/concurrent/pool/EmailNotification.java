package ru.job4j.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Creates email's content and body and send the email by the ExecutorService
     * @param user User object
     * @return Future<String> - in "{the user's name}-done" format
     */
    public Future<String> emailTo(User user) {
        String subj = "Notification " + user.getUserName() + " to email " + user.getEmail();
        String body = "Add a new event to " + user.getUserName();
        Runnable job = () -> send(subj, body, user.getEmail());
        return pool.submit(job, user.getUserName() + "-done");
    }

    /**
     * Shuts down all the tasks of sending and waits for their ending
     */
    public void  close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends an email with specified properties
     * @param subject email's subject
     * @param body email'body
     * @param email email
     */
    public void send(String subject, String body, String email) {
        //do something
    }
}
