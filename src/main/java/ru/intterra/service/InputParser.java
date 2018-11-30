package ru.intterra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intterra.domain.Email;
import ru.intterra.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private static final Logger log = LoggerFactory.getLogger(InputParser.class);

    public List<User> parse(List<String> lines) {
        List<User> users = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split("->");
            if (split.length < 2 || split[0].trim().isEmpty()) {
                log.warn("Incorrect format: {}", line);
                continue;
            }
            User user = new User(split[0].trim());
            user.setEmails(Arrays.stream(split[1].split(",")).filter(s -> !s.trim().isEmpty()).map(Email::new).collect(Collectors.toSet()));
            users.add(user);
        }
        return users;
    }
}
