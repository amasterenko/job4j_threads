package ru.job4j.concurrent.pool;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EmailNotificationTest {
    @Test
    public void whenAdd5Users() {
        EmailNotification emailNot = new EmailNotification();
        List<Future<String>> resultsOfFutures = new LinkedList<>();
        List<String> resultsOfStrings = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            String name = "user" + i;
            String email = "email" + i;
            User user = new User(name, email);
            resultsOfFutures.add(emailNot.emailTo(user));
        }
        emailNot.close();
        resultsOfFutures.forEach(f -> {
            try {
                resultsOfStrings.add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        assertThat(resultsOfStrings, is(List.of(
                "user1-done", "user2-done", "user3-done", "user4-done", "user5-done" )));
    }
}