package ru.intterra.domain;

import java.util.Objects;

public class Email {

    private final String value;

    public Email(String value) {
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        //noinspection StringEquality
        return (value == email.value) || (value != null && value.equalsIgnoreCase(email.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }
}
