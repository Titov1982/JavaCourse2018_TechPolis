package ru.tai.controllers.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import ru.tai.controllers.auth.AuthController;
import ru.tai.model.Token;
import ru.tai.model.User;
import ru.tai.servise.TokenAndUserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Map;

@Controller
@Path("/data")
public class DataController {

    private static final Logger log = LoggerFactory.getLogger(DataController.class);

    /**
     *  Внедряем репозиторий с токенами, где будут храниться токены зарегистрированных пользователей
     */
    @Autowired
    TokenAndUserRepository tokenAndUserRepository;

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public String users(){

        log.info(tokenAndUserRepository.toString());
        return tokenAndUserRepository.toString();
    }
}
