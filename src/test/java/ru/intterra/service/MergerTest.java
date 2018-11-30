package ru.intterra.service;

import org.junit.Test;
import ru.intterra.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.intterra.TestUtils.makeEmails;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class MergerTest {

    private final Merger merger = new Merger();

    @Test
    public void testBase() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("aaa", makeEmails("2@bk.ru", "3@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(1, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru", "3@bk.ru"), result.get(0).getEmails());
    }

    @Test
    public void testCaseInsensitive() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@BK.RU"));
        User user2 = new User("aaa", makeEmails("2@bk.ru", "3@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(1, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru", "3@bk.ru"), result.get(0).getEmails());
    }

    @Test
    public void testTransitive() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("aaa", makeEmails("2@bk.ru", "3@bk.ru"));
        User user3 = new User("aaa", makeEmails("3@bk.ru", "4@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2, user3));
        assertEquals(1, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru", "3@bk.ru", "4@bk.ru"), result.get(0).getEmails());
    }

    @Test
    public void testSameUserAndEmailsList() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(1, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru"), result.get(0).getEmails());
    }

    @Test
    public void testMatchByMultipleEmails() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru", "3@bk.ru"));
        User user2 = new User("aaa", makeEmails("2@bk.ru", "3@bk.ru", "4@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(1, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru", "3@bk.ru", "4@bk.ru"), result.get(0).getEmails());
    }

    @Test
    public void testDifferentUsers() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("bbb", makeEmails("4@bk.ru", "3@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(2, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru"), result.stream().filter(u -> u.getName().equals("aaa")).findAny().get().getEmails());
        assertEquals(makeEmails("3@bk.ru", "4@bk.ru"), result.stream().filter(u -> u.getName().equals("bbb")).findAny().get().getEmails());
    }

    @Test
    public void testEmptyUser() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("bbb", makeEmails());
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(2, result.size());
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru"), result.stream().filter(u -> u.getName().equals("aaa")).findAny().get().getEmails());
        assertTrue(result.stream().filter(u -> u.getName().equals("bbb")).findAny().get().getEmails().isEmpty());
    }

    @Test
    public void testAnyNamePicked() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("bbb", makeEmails("2@bk.ru", "3@bk.ru"));
        List<User> result = merger.merge(Arrays.asList(user1, user2));
        assertEquals(1, result.size());
        assertTrue("aaa".equals(result.get(0).getName()) || "bbb".equals(result.get(0).getName()));
        assertEquals(makeEmails("1@bk.ru", "2@bk.ru", "3@bk.ru"), result.get(0).getEmails());
    }
}