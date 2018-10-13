package ru.tai;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.tai.model.Message;
import ru.tai.model.Token;
import ru.tai.model.Userr;
import ru.tai.services.MessageService;
import ru.tai.services.UserService;
import ru.tai.services.dao.MessageRepository;
import ru.tai.services.dao.TokenRepository;
import ru.tai.services.dao.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ChatServer implements CommandLineRunner {
//    @Autowired
////    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    public static void main( String[] args )
    {
        SpringApplication.run(ChatServer.class);
    }

    @Override
    public void run(String... args) throws Exception {


        userService.addNewUser("titov", "123");
        Token token1 = userService.loginUser("titov", "123");
//        userService.deleteTokenUser(token1);

        userService.sandMessageUser(token1, "Это новый юзер titov!");
        userService.sandMessageUser(token1, "Это опять titov!");

        userService.addNewUser("andrey", "456");
        Token token2 = userService.loginUser("andrey", "456");
        userService.sandMessageUser(token2, "Это новый юзер andrey!");

        List<Message> messages = messageService.getAllMessageByUser(userService.getUserByToken_id(token1));

//        userService.addNewUser("Tom", "222");
//
//        userService.updateLoginUser(token2, "and");
//        userService.sandMessageUser(token2, "Я изменил имя!");
//        userService.updatePasswordUser(token2, "890");
//
//        Token token3 = userService.loginUser("Tom", "222");
//        userService.deleteUser(token3);


        // Получение всех пользователей
//        Iterable<Userr> users = userRepository.findAll();
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Userr user : users) {
//            System.out.println(user);
//        }
//        System.out.println();
//
        // Получение пользователя по логину
//        System.out.println(userRepository.findByLogin("titov"));
        // Получение пользователя по токену
//        System.out.println(userRepository.findByToken_id(new Token(new Long(12345))));

        // Добавление сообщения, которое передает пользователь
//        Userr user = userRepository.findByLogin("titov");
//        List<Message> mesList = new ArrayList<>();
//        mesList.add(new Message("Hello!", user));
//        user.setMessage(mesList);
//        userRepository.save(user);

//        mesList.clear();
//        // Добавление сообщения, которое передает пользователь
//        mesList.add(new Message("Hello!!!!!!!!!!!!!!!!!", user));
//        user.setMessage(mesList);
//        userRepository.save(user);

        // Находим пользователя по токену и выводим все его сообщения
//        Userr uu = userRepository.findByToken_id(new Token(new Long(12345)));
//        System.out.println();
//        System.out.println("--------- Все сообщения пользователя " + uu.getLogin() + " :");
//        List<Message> messages = uu.getMessage();
//        for (Message mes: messages) {
//            System.out.println(mes.getDateTime() + ": " + mes.getMessage());
//        }

        // Находим пользователя по логину, изменяем логин, пользователь пишет сообщение
//        Userr updatedUser = userRepository.findByLogin("andrey");
//        updatedUser.setLogin("and");
//        List<Message> newMess = new ArrayList<>();
//        newMess.add(new Message("Я изменил имя", updatedUser));
//        updatedUser.setMessage(newMess);
//        userRepository.save(updatedUser);

        // Находим все сообщения пользователя
//        List<Message> updatedUserMess = messageRepository.findByUser(updatedUser);
//        System.out.println("--------- Все сообщения пользователя " + updatedUser.getLogin() + " :");
//        for (Message mes: updatedUserMess) {
//            System.out.println(mes.getDateTime() + ": " + mes.getMessage());
//        }


        //userRepository.delete(uu);

     }
}
