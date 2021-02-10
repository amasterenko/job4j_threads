package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    final Map<Integer, User> users = new HashMap<>();

    synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!users.containsKey(fromId)
                || !users.containsKey(toId)
                || users.get(fromId).getAmount() < amount) {
            return false;
        }
        User curUser = users.get(fromId);
        curUser.setAmount(curUser.getAmount() - amount);
        curUser = users.get(toId);
        curUser.setAmount(curUser.getAmount() + amount);
        return true;
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        User u1 = new User(1, 100);
        User u2 = new User(2, 200);
        storage.add(u1);
        storage.add(u2);
        storage.transfer(1, 2, 50);
        System.out.println(u1);
        System.out.println(u2);
    }
}
