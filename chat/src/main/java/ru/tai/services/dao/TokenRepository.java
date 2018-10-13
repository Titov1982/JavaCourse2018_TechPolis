package ru.tai.services.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tai.model.Message;
import ru.tai.model.Token;
import ru.tai.model.Userr;

import java.util.Iterator;
import java.util.List;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    default boolean deleteByToken_id(Token token) {
        Iterable<Token> tokenList = findAll();
        for (Token t: tokenList) {
            if (t.getToken_id().equals(token.getToken_id())){
                delete(t);
                return true;
            }
        }
        return false;
    }
}
