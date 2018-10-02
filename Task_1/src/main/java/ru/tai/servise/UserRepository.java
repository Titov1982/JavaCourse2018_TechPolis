package ru.tai.servise;

import ru.tai.model.User;

public interface UserRepository {

    boolean addUser(User user);
    boolean deleteUser(User user);
    boolean deleteUserByName(String name);
    User getUserByName(String name);
}
