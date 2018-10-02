package ru.tai.servise;

import org.springframework.stereotype.Service;
import ru.tai.model.User;

import java.util.HashSet;
import java.util.Set;


/**
 * Сервис для управления репозиторием пользователей
 */
@Service
public class UserRepositoryInMemory implements UserRepository{

    // Хранилище пользователей
    private Set<User> users = new HashSet<>();

    /**
     * Метод добавляет пользователя в хранилище, если пользователя с таким именем там еще нет
     * @param user - объект пользователя
     * @return - результат true/talse
     */
    public boolean addUser(User user){
        if(users.contains(user)){
            return false;
        }
        users.add(user);
        return true;
    }

    /**
     * Метод удаляет пользователя из хранилище, если пользователя с таким именем там уже есть
     * @param user - объект пользователя
     * @return - результат true/talse
     */
    public boolean deleteUser(User user){
        if (users.isEmpty()) return false;
        if(!users.contains(user)) return false;
        users.remove(user);
        return true;
    }

    /**
     * Метод удаляет пользователя из хранилище по имени, если пользователя с таким именем там уже есть
     * @param name - строковое имя пользователя
     * @return - результат true/talse
     */
    public boolean deleteUserByName(String name) {
        if (users.isEmpty()) return false;
        for (User u : users) {
            if (u.getName().equals(name)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    /**
     * Метод получает объект пользователя из хранилище по имени, если пользователя с таким именем там уже есть
     * @param name - строковое имя пользователя
     * @return - результат User/null
     */
    public User getUserByName(String name){
        if (users.isEmpty()) return null;
        for (User u: users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }




}
