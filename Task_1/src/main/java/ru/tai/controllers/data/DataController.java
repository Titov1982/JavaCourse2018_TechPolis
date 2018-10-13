package ru.tai.controllers.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import ru.tai.servise.TokenAndUserRepositoryInMemory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Controller
@Path("/data")
public class DataController {

    private static final Logger log = LoggerFactory.getLogger(DataController.class);

    /**
     *  Внедряем репозиторий с токенами, где будут храниться токены зарегистрированных пользователей
     */
    @Autowired
    TokenAndUserRepositoryInMemory tokenAndUserRepository;

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
//    public String users(){
//
//        log.info(tokenAndUserRepository.toString());
//        return tokenAndUserRepository.toString();
//    }
    public Response users(){

        log.info(tokenAndUserRepository.toString());

        return Response.status(200).entity(tokenAndUserRepository.toString()).build();
    }
}
