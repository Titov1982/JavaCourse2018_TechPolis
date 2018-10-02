package ru.tai.model;

import java.util.Objects;

public class Token {

    private final Long token;

    public Token(Long token) {
        this.token = token;
    }

    public Long getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token=" + token +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {

        return Objects.hash(token);
    }
}
