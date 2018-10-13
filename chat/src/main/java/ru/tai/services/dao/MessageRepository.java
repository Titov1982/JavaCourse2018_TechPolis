package ru.tai.services.dao;

import org.springframework.data.repository.CrudRepository;
import ru.tai.model.Message;
import ru.tai.model.Token;
import ru.tai.model.Userr;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByUser(Userr user);

}
