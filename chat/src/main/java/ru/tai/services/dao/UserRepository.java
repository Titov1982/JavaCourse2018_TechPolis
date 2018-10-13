package ru.tai.services.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tai.model.Token;
import ru.tai.model.Userr;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<Userr, Long> {

    Userr findByLogin(String login);

//    Userr findByToken_id(Token token);


    default Userr findByToken_id(Token token){
        Iterable<Userr> users = findAll();
        for (Userr user : users) {
            if (user.getToken_id() != null) {
                if (user.getToken_id().getToken_id().equals(token.getToken_id())) {
                    return user;
                }
            }
        }
        return null;
    }

}
