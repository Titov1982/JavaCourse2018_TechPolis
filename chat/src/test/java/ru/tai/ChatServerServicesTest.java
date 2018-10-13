package ru.tai;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tai.model.Token;
import ru.tai.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatServer.class)
public class ChatServerServicesTest
{
    @Autowired
    UserService userService;

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void addNewUserTrueTest(){
        userService.deleteUser("titov", "123");
        boolean res = userService.addNewUser("titov", "123");
        userService.deleteUser("titov", "123");
        Assert.assertTrue(res);
    }

    @Test
    public void addNewUserFalseTest(){
        userService.addNewUser("titov", "123");
        boolean res2 = userService.addNewUser("titov", "123");
        Assert.assertFalse(res2);
    }

    @Test
    public void loginNewUserTest(){
        userService.deleteUser("titov", "123");
        Token token = null;
        if (userService.addNewUser("titov", "123")){
            token = userService.loginUser("titov", "123");
        }
        Assert.assertTrue(token != null && token.getToken_id() != 0);
    }

    @Test
    public void loginRegisteredUserTest(){
        Token token = null;
        if (!userService.addNewUser("titov", "123")){
            token = userService.loginUser("titov", "123");
        }
        Assert.assertTrue(token != null && token.getToken_id() != 0);
    }

    @Test
    public void deleteTokenFromUserTest(){
        userService.addNewUser("titov", "123");
        Token token1 = userService.loginUser("titov", "123");
        boolean res = userService.deleteTokenUser(token1);
        Assert.assertTrue(res);
    }


}
