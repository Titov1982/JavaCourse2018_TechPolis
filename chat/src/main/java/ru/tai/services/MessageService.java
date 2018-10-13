package ru.tai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tai.model.Message;
import ru.tai.model.Token;
import ru.tai.model.Userr;
import ru.tai.services.dao.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getAllMessageByUser(Userr user){
        return messageRepository.findByUser(user);
    }

}
