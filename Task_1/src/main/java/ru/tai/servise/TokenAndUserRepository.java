package ru.tai.servise;

import ru.tai.model.Token;
import ru.tai.model.User;

import java.util.List;

public interface TokenAndUserRepository {

    boolean addTokenAndUser(Token token, User user);
    boolean containsToken(Token token);
    User getUserByToken(Token token);
    Token getTokenByUser(User user);
    boolean deleteUserByToken(Token token);
    List<User> getAllLoginUsers();
    String toString();

}
