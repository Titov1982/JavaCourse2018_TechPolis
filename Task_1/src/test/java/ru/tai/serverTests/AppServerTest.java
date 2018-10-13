package ru.tai.serverTests;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tai.AppServer;
import ru.tai.controllers.auth.AuthController;
import ru.tai.controllers.data.DataController;

import javax.ws.rs.core.Response;


//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServer.class)
public class AppServerTest {

    private static final Logger log = LoggerFactory.getLogger(AppServerTest.class);

    @Autowired
    private AuthController authController;

    @Autowired
    private DataController dataController;

    @Test
    public void contextLoads() throws Exception {

        Assert.assertNotNull(authController);
    }

    @Test
    public void registerTrue(){
        Response responseRegister = authController.register("titov", "123");
        String bodyMessage = responseRegister.getEntity().toString();
        log.info("registerTrue -> BODY: " + "Code= " + responseRegister.getStatus() + " Message= " + responseRegister.getEntity().toString());

        Assert.assertTrue(responseRegister.getStatus() == 200 &&
                responseRegister.getEntity().toString() == "Пользователь успешно зарегистрирован!");
    }

    @Test
    public void registerFalse(){
        Response responseRegister = authController.register("titov", "123");
        log.info("registerFalse -> BODY: " + "Code= " + responseRegister.getStatus() + " Message= " + responseRegister.getEntity().toString());

        Assert.assertTrue(responseRegister.getStatus() == 409 &&
                responseRegister.getEntity().toString() == "Пользователь с таким именем уже зарегистрирован!");
    }

    @Test
    public void loginTrue(){
        authController.register("andrey", "456");
        Response responseLogin  = authController.login("andrey", "456");

        log.info("loginTrue -> BODY: " + "Code= " + responseLogin.getStatus() + " Message= " + responseLogin.getEntity().toString());

        Assert.assertTrue(responseLogin.getStatus() == 200);
        Assert.assertNotNull(responseLogin.getEntity());
    }

    @Test
    public void loginFalse(){
        authController.register("andrey", "456");
        Response responseLogin  = authController.login("and", "000");

        log.info("loginFalse -> BODY: " + "Code= " + responseLogin.getStatus() + " Message= " + responseLogin.getEntity().toString());

        Assert.assertTrue(responseLogin.getStatus() == 401 &&
                responseLogin.getEntity().toString() == "Введено не верное имя пользователя или пароль!");

    }

    @Test
    public void viewOnline(){
        authController.register("aaa", "123");
        Response responseLogin = authController.login("aaa", "123");

        Response responseUsers = dataController.users();

        log.info("viewOnline -> BODY: " + "Code= " + responseUsers.getStatus() + " Message= " + responseUsers.getEntity().toString());

        Assert.assertTrue(responseUsers.getStatus() == 200 &&
                responseUsers.getEntity().toString().equals("{\"users\" : [{aaa}]}"));

    }

    @Test
    public void logoutTrue(){
        authController.register("bbb", "123");
        Response responseLogin = authController.login("bbb", "123");
        String token = responseLogin.getEntity().toString();

        Response responseLogout = authController.logout("Bearer " + token);

        Assert.assertTrue(responseLogout.getStatus() == 200 &&
                responseLogout.getEntity().toString().equals("Пользователь вышел из системы!"));
    }

    @Test
    public void logoutFalse(){

        Response responseLogout = authController.logout("Bearer " + "0000");

        Assert.assertTrue(responseLogout.getStatus() == 409 &&
                responseLogout.getEntity().toString().equals("Пользователь не был удален, так как предъявлен не существующий токен!"));
    }
}
