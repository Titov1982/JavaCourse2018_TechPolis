package ru.tai.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "token_id")
    private Long token_id;

    @OneToOne(mappedBy = "token_id", cascade = CascadeType.ALL)
    private Userr user;

    private Token(){}

    public Token(Long token_id) {
        this.token_id = token_id;
    }

    public Long getId() {
        return id;
    }

    public Long getToken_id() {
        return token_id;
    }

    public void setToken_id(Long token_id) {
        this.token_id = token_id;
    }

    public Userr getUser() {
        return user;
    }

    public void setUser(Userr user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token=" + token_id +
                '}';
    }
}
