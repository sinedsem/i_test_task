package ru.intterra.service;

import ru.intterra.domain.Email;
import ru.intterra.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class Merger {

    public List<User> merge(List<User> source) {
        Map<Email, List<User>> usersByEmail = source.stream()
                .map(user -> user.getEmails().stream().collect(Collectors.toMap(e -> e, e -> user)))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        Set<User> notMergedUsers = new HashSet<>(source);

        List<User> result = new ArrayList<>(source.size());

        while (!notMergedUsers.isEmpty()) {
            User user = notMergedUsers.iterator().next();

            Set<Email> emailsToCheck = new HashSet<>(user.getEmails());
            Set<Email> foundEmails = new HashSet<>(user.getEmails());
            Set<Email> checkedEmails = new HashSet<>();

            while (!emailsToCheck.isEmpty()) {
                foundEmails.addAll(emailsToCheck.stream()
                        .flatMap(key -> usersByEmail.get(key).stream().flatMap(u -> u.getEmails().stream()))
                        .collect(Collectors.toSet()));
                checkedEmails.addAll(emailsToCheck);

                emailsToCheck = new HashSet<>(foundEmails);
                emailsToCheck.removeAll(checkedEmails);
            }
            result.add(new User(user.getName(), foundEmails));
            notMergedUsers.remove(user); // for users which don't have any email
            notMergedUsers.removeAll(foundEmails.stream().flatMap(email -> usersByEmail.get(email).stream()).collect(Collectors.toSet()));
        }
        return result;
    }
}
