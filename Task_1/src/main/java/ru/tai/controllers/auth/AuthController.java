package ru.tai.controllers.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import ru.tai.model.Token;
import ru.tai.model.User;
import ru.tai.servise.TokenAndUserRepository;
import ru.tai.servise.UserRepository;
import ru.tai.util.Util;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


/**
 * Контроллер авторизации
 *
 * Реализует методы:
 *
 *  1. register
 *  2. login
 *  3. logout
 *
 */

@Controller
@Path("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    /**
     *  Внедряем репозиторий пользователей, где будут храниться зарегистрированные пользователи
     */
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenAndUserRepository tokenAndUserRepository;


    /**
     * В конструкторе выводим в лог информацию, что контроллер создан.
     */
    public AuthController() {
        log.info("Controller was created --> " + this.getClass().getName());
    }



    /**
     * @param input - тесторый параметр для проверки аннотации @QueryParam. Эта аннотация используется для запроса GET
     *
     * Пример запроса:
     * curl -X GET -i http://localhost:8080/auth/test?input=aaaaaa
     */
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN_VALUE  + "; charset=UTF-8")
    public String test(@QueryParam("input") String input){
        log.info("Hello from test method! input= " + input +" :)");
        return "Hello from test method! input= " + input +" :)\n\n";
    }



    /**
     * Метод для регистрации пользователя на сервере
     *
     * @param name - имя пользователя
     * @param password - пароль пользователя
     * @return
     *
     * Для запроса типа POST используется аннотоция @FormParam.
     * В одном запросе можно использовать оба типа аннотация:
     * @FormParam - передается через форму
     * @QueryParam - через запрос как в случае с запросом типа GET
     *
     * Пример запроса:
     * curl -X POST -i localhost:8080/auth/register -d "name=titov&password=123"
     */
    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN_VALUE  + "; charset=UTF-8") //@Produces("text/plain;charset=UTF-8")
    public Response register(@NotNull @FormParam("name") String name, @NotNull @FormParam("password") String password){

        // Создаем пользователя и добавляем его в репозиторий пользователей, если добавление прошло успешно,
        // то получаем true, иначе false.
        // Добавление не проходит, если пользователь с таким именем уже существует
        boolean res = userRepository.addUser(new User(name, password));

        // Если такой пользователь уже существует, то формируем тело ответа с HTTP кодом 409 - Conflict и
        // выводим сообщение об ошибке
        if (!res){
            log.info("Пользователь с таким именем уже зарегистрирован!");
            return Response.status(409).entity("Пользователь с таким именем уже зарегистрирован!").build();
        }

        // Если пользователь успешно добавлен, то формируем тело ответа с HTTP кодом 200 - OK и
        // выводим сообщение об успехе
        log.info("Пользователь успешно зарегистрирован: name= " + name + " pass= " + password);
        return Response.status(200).entity("Пользователь успешно зарегистрирован!").build();
    }


    /**
     *
     * @param name
     * @param password
     * @return
     */
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN_VALUE  + "; charset=UTF-8")
    @Secured
    public Response login(@NotNull @FormParam("name") String name, @NotNull @FormParam("password") String password){

        User user = userRepository.getUserByName(name);

        if (user == null|| (!(user.getName().equals(name) && user.getPasswoord().equals(password)))){
            log.info("Введено не верное имя пользователя или пароль!");
            return Response.status(401).entity("Введено не верное имя пользователя или пароль!").build();
        }

        Token token = null;

        token = tokenAndUserRepository.getTokenByUser(user);

        if (token == null){
            token = Util.requestToken(user);
            tokenAndUserRepository.addTokenAndUser(token, user);
        }

        log.info("Вы залогинились под именем name= " + name + " с токеном= " + token.getToken());
        return Response.status(200).entity(token.getToken()).build();
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.TEXT_PLAIN_VALUE  + "; charset=UTF-8")
    @Secured
    public Response logout(@HeaderParam("Authorization") String header) {
        String tokenStr = header.substring("Bearer".length()).trim();

        Token token = new Token(Long.parseLong(tokenStr));

        boolean res = tokenAndUserRepository.deleteUserByToken(token);

        if (!res){
            log.info("Пользователь не был удален, так как преъявлен не существующий токен!");
            return Response.status(409).entity("Пользователь не был удален, так как предъявлен не существующий токен!").build();
        }

        log.info("Поьзователь вышел из системы!");
        return Response.status(200).entity("Пользователь вышел из системы!").build();
    }
}
