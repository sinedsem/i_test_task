package ru.intterra;

import ru.intterra.domain.Email;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class TestUtils {

    private TestUtils() {
    }

    public static Set<Email> makeEmails(String... emails) {
        return Arrays.stream(emails).map(Email::new).collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
