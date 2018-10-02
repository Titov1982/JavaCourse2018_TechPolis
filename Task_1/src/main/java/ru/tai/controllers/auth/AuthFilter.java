package ru.tai.controllers.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.tai.model.Token;
import ru.tai.servise.TokenAndUserRepository;
import ru.tai.servise.UserRepository;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Фильтр авторизации пользователей.
 * Активируется перед вызовом контроллеров, которые аннотированы анотацией @Secured.
 * Проверяет имеется ли в запросе заголовок авторизации и если да, то проверяет есть ли представленный в заголовке
 * токен в базе.
 */

@Controller
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    /**
     *  Внедряем репозиторий пользователей, где будут храниться зарегистрированные пользователи
     */
    @Autowired
    UserRepository userRepository;

    /**
     *  Внедряем репозиторий с токенами, где будут храниться токены зарегистрированных пользователей
     */
    @Autowired
    TokenAndUserRepository tokenAndUserRepository;


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader==null || !authHeader.startsWith("Bearer")){
            log.info("Заголовок не имеет авторизации!!!");
//            containerRequestContext.setRequestUri(URI.create("/login"));
//            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Извлекаеи токен
        String tokenStr = authHeader.substring("Bearer".length()).trim();
        // Создаем обхект Token
        Token token = new Token(Long.parseLong(tokenStr));

        if (!tokenAndUserRepository.containsToken(token)){
            log.info("Пользователь не авторизован!!!");
//            containerRequestContext.setRequestUri(URI.create("/login"));
//            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        log.info("ВЫХОД ИЗ ФИЛЬТРА!");
        return;
    }

}
