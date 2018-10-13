package ru.tai.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
    private Userr user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateTime")
    private Date dateTime;

    private Message(){}

    @Deprecated
    public Message(String message) {
        this.message = message;
        this.dateTime = new Date();
    }

    public Message(String message, Userr user) {
        this.message = message;
        this.user = user;
        this.dateTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Userr getUser() {
        return user;
    }

    public void setUser(Userr user) {
        this.user = user;
    }

    public Date getDateTime() {
        return dateTime;
    }
}
