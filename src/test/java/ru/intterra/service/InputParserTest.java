package ru.intterra.service;

import org.junit.Test;
import ru.intterra.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.intterra.TestUtils.makeEmails;

public class InputParserTest {

    private final InputParser inputParser = new InputParser();

    @Test
    public void testBase() {
        List<User> result = inputParser.parse(Arrays.asList("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru", "user2 -> foo@gmail.com, ups@pisem.net"));
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals(makeEmails("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"), result.get(0).getEmails());
        assertEquals("user2", result.get(1).getName());
        assertEquals(makeEmails("foo@gmail.com", "ups@pisem.net"), result.get(1).getEmails());
    }

    @Test
    public void testNoSpaces() {
        List<User> result = inputParser.parse(Arrays.asList("user1->xxx@ya.ru,foo@gmail.com,lol@mail.ru","user2->foo@gmail.com,ups@pisem.net"));
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals(makeEmails("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"), result.get(0).getEmails());
        assertEquals("user2", result.get(1).getName());
        assertEquals(makeEmails("foo@gmail.com", "ups@pisem.net"), result.get(1).getEmails());
    }

    @Test
    public void testExtraCommas() {
        List<User> result = inputParser.parse(Arrays.asList("user1 -> xxx@ya.ru,, foo@gmail.com,, lol@mail.ru", "user2 -> foo@gmail.com, ,ups@pisem.net"));
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals(makeEmails("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"), result.get(0).getEmails());
        assertEquals("user2", result.get(1).getName());
        assertEquals(makeEmails("foo@gmail.com", "ups@pisem.net"), result.get(1).getEmails());
    }

    @Test
    public void testErrorStrings() {
        List<User> result = inputParser.parse(Arrays.asList("user1 -> xxx@ya.ru,, foo@gmail.com,, lol@mail.ru", "user2 <- foo@gmail.com, ups@pisem.net"));
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals(makeEmails("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"), result.get(0).getEmails());
    }

    @Test
    public void testUserWithoutEmails() {
        List<User> result = inputParser.parse(Arrays.asList("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru", "user2 -> "));
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals(makeEmails("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"), result.get(0).getEmails());
        assertEquals("user2", result.get(1).getName());
        assertTrue(result.get(1).getEmails().isEmpty());
    }

    @Test
    public void testDuplicatedEmails() {
        List<User> result = inputParser.parse(Arrays.asList("user1 -> xxx@ya.ru, xXx@ya.ru, xxx@ya.ru, XXX@ya.ru, ", "user2 -> foo@gmail.com, ups@pisem.net"));
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals(makeEmails("xxx@ya.ru"), result.get(0).getEmails());
        assertEquals("user2", result.get(1).getName());
        assertEquals(makeEmails("foo@gmail.com", "ups@pisem.net"), result.get(1).getEmails());
    }

}