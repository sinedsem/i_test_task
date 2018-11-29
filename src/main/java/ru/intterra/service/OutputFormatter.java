package ru.intterra.service;

import ru.intterra.domain.Email;
import ru.intterra.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class OutputFormatter {


    public List<String> format(List<User> users) {
        return users.stream()
                .map(user ->
                        String.format("%s -> %s",
                                user.getName(),
                                user.getEmails().stream()
                                        .map(Email::getValue)
                                        .collect(Collectors.joining(", "))))
                .collect(Collectors.toList());
    }
}
