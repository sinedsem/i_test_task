package ru.intterra.service;

import ru.intterra.domain.Email;
import ru.intterra.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class Merger {

    public List<User> merge(List<User> source) {
        Map<Email, Holder> map = new HashMap<>();

        List<Holder> holders = new ArrayList<>(source.size());
        for (User user : source) {
            Holder holder = new Holder();
            holder.users.add(user);

            for (Email email : user.getEmails()) {
                Holder foundHolder = map.get(email);
                if (foundHolder != null) {
                    holder.users.addAll(foundHolder.another.users);
                    foundHolder.another = holder;
                }
            }
            for (Email email : user.getEmails()) {
                map.put(email, holder);
            }
            holders.add(holder);
        }

        List<User> result = new ArrayList<>(source.size());
        for (Holder holder : holders) {
            if (holder.another == holder) {
                result.add(new User(holder.users.iterator().next().getName(), holder.users.stream().flatMap(u -> u.getEmails().stream()).collect(Collectors.toSet())));
            }
        }
        return result;
    }

    private static class Holder {
        private final Set<User> users = new HashSet<>();
        private Holder another = this;
    }
}
