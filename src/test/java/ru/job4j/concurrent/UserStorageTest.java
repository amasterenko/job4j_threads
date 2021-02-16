package ru.job4j.concurrent;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import java.util.Collections;
import java.util.Map;

public class UserStorageTest {

    @Test
    public void whenAddTwoDifUsers() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(2, 200);
        Thread first = new Thread(() -> storage.add(u1));
        Thread second = new Thread(() -> storage.add(u2));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.getUsers(), is(Map.of(1, u1, 2, u2)));
    }

    @Test
    public void whenAddTheSameUsers() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(1, 200);
        Thread first = new Thread(() -> storage.add(u1));
        Thread second = new Thread(() -> storage.add(u2));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.getUsers(), is(Map.of(1, u1)));
    }

    @Test
    public void whenUpdateUser() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(1, 200);
        Thread first = new Thread(() -> storage.add(u1));
        Thread second = new Thread(() -> storage.update(u2));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.getUsers(), is(Map.of(1, u2)));
    }

    @Test
    public void whenUpdateNotExistedUser() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(2, 200);
        Thread first = new Thread(() -> storage.add(u1));
        Thread second = new Thread(() -> storage.update(u2));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.getUsers(), is(Map.of(1, u1)));
    }

    @Test
    public void whenDeleteUser() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        Thread first = new Thread(() -> storage.add(u1));
        Thread second = new Thread(() -> storage.delete(u1));
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(storage.getUsers(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void whenDeleteNotExistedUser() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(2, 200);
        Thread first = new Thread(() -> storage.add(u1));
        Thread second = new Thread(() -> storage.delete(u2));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.getUsers(), is(Map.of(1, u1)));
    }

    @Test
    public void whenTransferFrom1To1User() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(2, 200);
        storage.add(u1);
        storage.add(u2);
        Thread first = new Thread(() -> storage.transfer(1, 2, 100));
        Thread second = new Thread(() -> storage.transfer(2, 1, 300));
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(storage.getUsers().get(1).getAmount(), is(300));
        assertThat(storage.getUsers().get(2).getAmount(), is(0));
    }

    @Test
    public void whenTransferFrom1To2Users() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(2, 200);
        User u3 = new User(3, 300);
        storage.add(u1);
        storage.add(u2);
        storage.add(u3);
        Thread first = new Thread(() -> storage.transfer(1, 2, 50));
        Thread second = new Thread(() -> storage.transfer(1, 3, 50));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.getUsers().get(1).getAmount(), is(0));
        assertThat(storage.getUsers().get(2).getAmount(), is(250));
        assertThat(storage.getUsers().get(3).getAmount(), is(350));
    }

}