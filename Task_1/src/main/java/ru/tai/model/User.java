package ru.tai.model;

import java.util.Objects;

public class User {

    public String getName() {
        return name;
    }

    public String getPasswoord() {
        return passwoord;
    }

    private final String name;
    private final String passwoord;

    public User(String name, String passwoord) {
        this.name = name;
        this.passwoord = passwoord;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", passwoord='" + passwoord + '\'' +
                '}';
    }


    /**
     * Переопределяем метод equals.
     * Будем считать, что объекты эквивалентными только если у них совпадает поле name.
     * Это сделано, для корректной работы методов contains из интерфейсов коллекций.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }


    /**
     * Переопределяем метод hashCode.
     * В расчете должны участвовать только те поля, по которым необходимо определять разниуц.
     * Это сделано, для корректной работы методов contains из интерфейсов коллекций.
     * В нашем случае поле password не участвует в расчете.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }
}
