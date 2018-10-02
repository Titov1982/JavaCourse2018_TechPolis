package ru.tai.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import ru.tai.controllers.auth.AuthController;
import ru.tai.controllers.auth.AuthFilter;
import ru.tai.controllers.data.DataController;

import javax.ws.rs.ApplicationPath;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ApplicationPath(value = "/api")
public class JerseyConfig extends ResourceConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    List<String> registeredControllersName = new ArrayList<>();

    public JerseyConfig() {

        register(AuthFilter.class);
        register(AuthController.class);
        register(DataController.class);

        //property(ServletProperties.FILTER_FORWARD_ON_404, true);

        registeredControllersName.add(AuthFilter.class.getName());
        registeredControllersName.add(AuthController.class.getName());
        registeredControllersName.add(DataController.class.getName());

        for (String className: registeredControllersName){
            log.info("Registered Jersey controller: " + className);
        }


    }

}