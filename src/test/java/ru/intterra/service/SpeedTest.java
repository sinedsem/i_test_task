package ru.intterra.service;

import org.junit.Test;
import ru.intterra.domain.Email;
import ru.intterra.domain.User;

import java.util.*;

/**
 * Test author: intterra.ru
 */
public class SpeedTest {

    private final Merger merger = new Merger();
    private final Random rnd = new Random();

    @Test
    public void speedTest() {
        // warming
        speedTest(2000, 2000, 200);

        for (int e = 100; e <= 6400; e *= 2) {
            speedTest(e);
        }
    }

    private void speedTest(int x) {
        long ms = speedTest(1000, x * 2, x);
        System.out.println(String.format("%d %d %g", x, ms, (double) x / (double) ms));
    }

    private long speedTest(int nUsers, int nEmails, int nEmailsPerUser) {
        // prepare data
        final Map<String, Set<String>> src = new HashMap<>(nUsers);

        for (int i = 0; i < nUsers; i++) {
            final Set<String> emails = new HashSet<>(nEmailsPerUser);
            while (emails.size() < nEmailsPerUser) {
                emails.add("user" + rnd.nextInt(nEmails) + "@ya.ru");
            }
            src.put("user" + i, emails);
        }

        long start = System.currentTimeMillis();
        // Fill input array
        List<User> users = new ArrayList<>(nUsers);
        for (Map.Entry<String, Set<String>> entry : src.entrySet()) {
            final Set<Email> emails = new HashSet<>(entry.getValue().size());
            for (String email : entry.getValue()) {
                emails.add(new Email(email));
            }
            users.add(new User(entry.getKey(), emails));
        }
        // perform merge
        merger.merge(users);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
