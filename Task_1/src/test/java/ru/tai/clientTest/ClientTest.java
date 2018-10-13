package ru.tai.clientTest;

import static org.junit.Assert.assertTrue;

import okhttp3.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tai.client.Client;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class ClientTest {
    private static final Logger log = LoggerFactory.getLogger(ClientTest.class);

    private static Client client = new Client();

    private static String tokenUser = "0";

    @Test
    public void register() throws IOException {
        Response responseRegister = client.register("titov", "123");

        String bodyMessage = responseRegister.body().string();

        log.info("Ответ на регистрацию пользователя: " + responseRegister.toString());
        log.info("Ответ на регистрацию пользователя: " + bodyMessage);

        Assert.assertTrue(responseRegister.code() == 200 ||
                bodyMessage.equals("Пользователь с таким именем уже зарегистрирован!"));
    }


    @Test
    public void loginTrue() throws IOException {
        Response responseLogin = client.login("titov", "123");

        tokenUser = responseLogin.body().string();

        log.info("Ответ на логин пользователя: " + responseLogin.toString());
        log.info("Ответ на запрос login -> выданный токен пользователя: " + tokenUser);

        Assert.assertTrue(responseLogin.code() == 200);
    }

    @Test
    public void loginFalse() throws IOException {
        Response responseLogin = client.login("aaa", "123");

        String bodyMessage = responseLogin.body().string();

        log.info("Ответ на запрос login: " + bodyMessage);

        Assert.assertTrue(responseLogin.code() == 401 &&
                bodyMessage.equals("Введено не верное имя пользователя или пароль!"));
    }

    @Test
    public void logoutTrue() throws IOException {
        Response responseLogout = client.logout(tokenUser);

        String bodyMessage = responseLogout.body().string();

        log.info("Ответ на логоут пользователя: " + responseLogout.toString());
        log.info("Ответ на регистрацию пользователя: " + bodyMessage);

        Assert.assertTrue(responseLogout.code() == 200 &&
                bodyMessage.equals("Пользователь вышел из системы!"));
    }

    @Test
    public void logoutFalse() throws IOException {

        Response responseLogout = client.logout("0000000");

        String bodyMessage = responseLogout.body().string();

        log.info("Ответ на логоут пользователя: " + responseLogout.toString());
        log.info("Ответ на регистрацию пользователя: " + bodyMessage);

        Assert.assertTrue(responseLogout.code() == 409 &&
                bodyMessage.equals("Пользователь не был удален, так как предъявлен не существующий токен!"));
    }

    @Test
    public void viewOnline() throws IOException {
        Response responseRegister = client.register("aaa", "890");
        Response responseLogin = client.login("aaa", "890");

        String tokenUser = responseLogin.body().string();

        Response responseUsers =  client.viewOnline();
        String bodyMessage = responseUsers.body().string();
        log.info("Ответ на запрос пользователей: " + responseUsers.toString());
        log.info("Список залогиненных пользователей: " + bodyMessage);

        Assert.assertTrue(responseUsers.code() == 200 &&
                bodyMessage.equals("{\"users\" : [{aaa}]}"));

        Response responseLogout = client.logout(tokenUser);


    }
}