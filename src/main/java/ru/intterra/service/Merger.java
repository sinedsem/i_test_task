package ru.intterra.service;

import ru.intterra.domain.Email;
import ru.intterra.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Merger {

    public List<User> merge(List<User> source) {
        Map<Email, User> map = new HashMap<>();
        List<User> result = new ArrayList<>();

        for (User user : source) {
            User u = null;
            for (Email email : user.getEmails()) {
                map.get(email);
                u = map.get(email);
                if (u != null) {
                    u.getEmails().addAll(u.getEmails());
                    break;
                }
            }
            if (u == null) {
                u = new User(user.getName());
                result.add(u);
            }

            u.getEmails().addAll(user.getEmails());
            for (Email email : user.getEmails()) {
                map.put(email, u);
            }
        }
        return result;
    }
}
