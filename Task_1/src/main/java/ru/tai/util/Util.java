package ru.tai.util;

import ru.tai.model.Token;
import ru.tai.model.User;

import java.util.Date;
import java.util.Random;

public class Util {

    /**
     * Статический метод рассчитывает токен на основании хеш-кода объекта и текущего времени, соответственно
     * для каждого объекта он будет уникальным при постоянном хеш-коде.
     * Также добавляем 1_000_000_000 для увеличения значения
     *
     * @param user - передаем объект User для извлечения хеш-кода
     * @return - возвращаем объект Token
     */
    public static Token requestToken(User user){

        // Формируем токен
        final Random defaultToken = new Random(1_000_000_000 + user.hashCode() + new Date().getTime());
        //Временно убрали добавление времени, для постоянства хеша для объекта, чтобы он не менялся со временем
//        final Random defaultToken = new Random(1_000_000_000 + user.hashCode());
        // Создаем объект Token
        Token token = new Token(defaultToken.nextLong());
        return token;
    }
}
