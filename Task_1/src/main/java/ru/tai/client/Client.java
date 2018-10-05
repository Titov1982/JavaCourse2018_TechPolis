package ru.tai.client;


import okhttp3.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    /**
     * Создаем клиента и формируем основные параметры запроса
     */
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    /**
     * Функция регистрации пользователя на сервере с помощью запроса типа POST.
     * Формируем запрос, так как параметры из формы должны передаваться в теле запроса, то
     * помещаем наши параметры в тело запроса.
     * Далее вызываем метод клиента для оправки сформированного запроса.
     *
     * @param name - имя пользователя
     * @param password - пароль пользователя
     * @return - возвращает ответ сервера
     * @throws IOException
     */

    //POST host:port/api/auth/register
    public static Response register(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/api/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    /**
     * Функция входа пользователя на сервере с помощью запроса типа POST.
     * Формируем запрос, так как параметры из формы должны передаваться в теле запроса, то
     * помещаем наши параметры в тело запроса.
     * Далее вызываем метод клиента для оправки сформированного запроса.
     *
     * @param name - имя пользователя
     * @param password - пароль пользователя
     * @return - возвращает ответ сервера - в теле находится токен клиента, который необходимо сохранить
     * @throws IOException
     */

    //POST host:port/api/auth/login
    public static Response login(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/api/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    /**
     * Функция выхода пользователя из сервере с помощью запроса типа POST
     * Формируем запрос, так как для работы залогиненного пользователя необходим токен, который
     * он сохранил при логине, то помещаем его в заголовок запроса, тело в данном методе остается пустым.
     * Заголовок ответа сервера формируется следующим образом. В заголовок добавляется название параметра
     * "Authorization:" и тип аутентификации "Bearer", а затем добавляется сам токен.
     * Далее вызываем метод клиента для оправки сформированного запроса.
     *
     *
     * @return - возвращает ответ сервера
     * @throws IOException
     */

    //POST host:port/api/auth/logout
    public static Response logout(String token) throws IOException {

        Headers.Builder headerBuilder = new Headers.Builder().add("Authorization: Bearer " + token);
        Headers requestHeader = headerBuilder.build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .headers(requestHeader)
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/api/auth/logout")
                .build();

        return client.newCall(request).execute();
    }

    /**
     * Метод выводит список всех залогиненных пользователей по запросу типа GET
     *
     * @return - возвращает ответ сервера
     * @throws IOException
     */
    //GET host:port/api/data/users
    public static Response viewOnline() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/data/users")
                .build();

        return client.newCall(request).execute();
    }



    public static void main(String[] args) throws IOException {

        String tokenUser = "";

        Response responseRegister = register("aaa", "890");
        log.info("Ответ на регистрацию пользователя: " + responseRegister.toString());
        log.info("Ответ на регистрацию пользователя: " + responseRegister.body().string());

        Response responseLogin = login("aaa", "890");
        tokenUser = responseLogin.body().string();
        log.info("Ответ на логин пользователя: " + responseLogin.toString());
        log.info("Ответ на запрос login -> выданный токен пользователя: " + tokenUser);

        Response responseUsers =  viewOnline();
        log.info("Ответ на запрос пользователей: " + responseUsers.toString());
        log.info("Список залогиненных пользователей: " + responseUsers.body().string());

        Response responseLogout = logout(tokenUser);
        log.info("Ответ на логоут пользователя: " + responseLogout.toString());
        log.info("Ответ на регистрацию пользователя: " + responseLogout.body().string());
    }

}
