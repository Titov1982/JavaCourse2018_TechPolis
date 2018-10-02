package ru.tai;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Запуск основного приложения - сервера
 */

@SpringBootApplication
public class AppServer
{
    public static void main( String[] args )
    {
        SpringApplication.run(AppServer.class, args);
    }
}
