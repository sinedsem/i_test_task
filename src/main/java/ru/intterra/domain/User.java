package ru.intterra.domain;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class User {

    private static long idGenerator = 0;

    private final long id = idGenerator++;
    private String name;
    private Set<Email> emails = Collections.emptySet();

    public User(String name) {
        this.name = name;
    }

    public User(String name, Set<Email> emails) {
        this.name = name;
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        if (emails == null) {
            this.emails = Collections.emptySet();
        }
        this.emails = emails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
