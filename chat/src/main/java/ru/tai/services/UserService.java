package ru.tai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tai.model.Message;
import ru.tai.model.Token;
import ru.tai.model.Userr;
import ru.tai.services.dao.TokenRepository;
import ru.tai.services.dao.UserRepository;
import ru.tai.util.Util;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    /**
     * Добавить нового пользователя в базу
     * @param login
     * @param password
     */
    public boolean addNewUser(String login, String password){
        Userr user = userRepository.findByLogin(login);
        if (user != null) return false;
        user = new Userr(login, password);
        userRepository.save(user);
        return true;
    }

    /**
     * Удалить пользователя из базы по логину и паролю
     * @param login
     * @param password
     * @return
     */
    public boolean deleteUser(String login, String password){
        Userr user = userRepository.findByLogin(login);
        if (user != null && user.getPassword().equals(password)){
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    /**
     * Удалить пользователя из базы по токену
     * @param token
     * @return
     */
    public boolean deleteUser(Token token){
        Userr user = userRepository.findByToken_id(token);
        if (user != null){
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    /**
     * Вход пользователя в систему. Если пользователь есть в базе, то проверяем есть ли у него токен.
     * Если токен есть то возвращаем его, если нет, то генерируем новый токен,
     * устанавливаем его для пользователя (сохраняем в базу), и возвращаем токен.
     * @param login
     * @param password
     * @return
     */
    public Token loginUser(String login, String password){
        Userr user = userRepository.findByLogin(login);
        if (user != null && user.getPassword().equals(password)){
            Token token = user.getToken_id();
            if (token != null) return token;

            token = Util.requestToken(user);
            user.setToken_id(token);
            token.setUser(user);
            userRepository.save(user);
            user = userRepository.findByLogin(login);
            return user.getToken_id();
        }
        return null;
    }

    /**
     * Записываем в базу сообщение пользователя. Пользователя определяем по токену.
     * @param token
     * @param message
     * @return
     */
    public boolean sandMessageUser(Token token, String message){
        Userr user = userRepository.findByToken_id(token);
        if (user == null) return false;

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(message, user));
        user.setMessage(messages);
        userRepository.save(user);
        return true;
    }

    public boolean updateLoginUser(Token token, String login){
        Userr user = userRepository.findByToken_id(token);
        Userr userWithUsedLogin = userRepository.findByLogin(login);
        if (user == null || userWithUsedLogin != null) return false;

        user.setLogin(login);
        userRepository.save(user);
        return true;
    }

    public boolean updatePasswordUser(Token token, String password){
        Userr user = userRepository.findByToken_id(token);
        if (user == null) return false;

        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    public boolean deleteTokenUser(Token token){
        Userr user = userRepository.findByToken_id(token);
        if (user == null) return false;

        user.deleteToken_id();
        userRepository.save(user);
        token.setUser(null);
        tokenRepository.delete(token);
        return true;
    }

    public Userr getUserByToken_id(Token token){
        return userRepository.findByToken_id(token);
    }
}
