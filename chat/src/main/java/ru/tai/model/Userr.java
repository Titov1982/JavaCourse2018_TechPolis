package ru.tai.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Userr {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "login", unique = true)
    private String login;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToOne(cascade = {CascadeType.ALL}/*, orphanRemoval = true*/)
//    @JoinColumn(name = "token_id")
    private Token token_id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registerDateTime")
    private Date registerDateTime;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> message;

    private Userr(){}

    public Userr(String login, String password) {
        this.login = login;
        this.password = password;
        this.registerDateTime = new Date();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login +
                ", token='" + token_id.getToken_id() + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Token getToken_id() {
        return token_id;
    }

    public void setToken_id(Token token_id) {
        this.token_id = token_id;
    }

    public void deleteToken_id(){
        this.token_id = null;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public Date getRegisterDateTime() {
        return registerDateTime;
    }

}
