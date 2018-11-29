package ru.intterra.service;

import org.junit.Test;
import ru.intterra.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.intterra.TestUtils.makeEmails;

public class OutputFormatterTest {

    private final OutputFormatter outputFormatter = new OutputFormatter();

    @Test
    public void testBase() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("aaa", makeEmails("2@bk.ru", "3@bk.ru"));
        List<String> result = outputFormatter.format(Arrays.asList(user1, user2));

        assertEquals(2, result.size());
        assertEquals("aaa -> 1@bk.ru, 2@bk.ru", result.get(0));
        assertEquals("aaa -> 2@bk.ru, 3@bk.ru", result.get(1));
    }

    @Test
    public void testUserWithNoEmails() {
        User user1 = new User("aaa", makeEmails("1@bk.ru", "2@bk.ru"));
        User user2 = new User("bbb");
        List<String> result = outputFormatter.format(Arrays.asList(user1, user2));

        assertEquals(2, result.size());
        assertEquals("aaa -> 1@bk.ru, 2@bk.ru", result.get(0));
        assertEquals("bbb -> ", result.get(1));
    }

}