package ru.tai.servise;

import org.springframework.stereotype.Service;
import ru.tai.model.Token;
import ru.tai.model.User;

import java.util.*;

@Service
public class TokenAndUserRepository {

    private Map<Token, User> tokenAndUserRepo = new HashMap<>();

    public boolean addTokenAndUser(Token token, User user){

        tokenAndUserRepo.put(token, user);
        return true;
    }

    public boolean containsToken(Token token){
        if (tokenAndUserRepo.containsKey(token))return true;
        return false;
    }

    public User getUserByToken(Token token){
        if (tokenAndUserRepo.containsKey(token)){
            for (Map.Entry<Token, User> entry : tokenAndUserRepo.entrySet()) {
                if (entry.getKey().equals(token)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public Token getTokenByUser(User user){
        if (!tokenAndUserRepo.isEmpty()){
            for (Map.Entry<Token, User> entry : tokenAndUserRepo.entrySet()) {
                if (entry.getValue().equals(user)){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public boolean deleteUserByToken(Token token){
        if (tokenAndUserRepo.containsKey(token)){
            tokenAndUserRepo.remove(token);
            return true;
        }
        return false;
    }

    public List<User> getAllLoginUsers(){
        List<User> users = new ArrayList<>();
        if (!tokenAndUserRepo.isEmpty()){
            for (Map.Entry<Token, User> entry : tokenAndUserRepo.entrySet()) {
                    users.add(entry.getValue());
            }
            return users;
        }
        return null;
    }


    /**
     * Этот метод ареобразует репозиторий в строку JSON вида:
     * {"users" : [{User1}, {User2}, ... ]}
     * @return - возвращает строку в виде JSON
     */
    @Override
    public String toString() {

        List<User> users = this.getAllLoginUsers();

        String strJSON = "{\"users\" : [";

        for (User user : users) {
            String strName = user.getName();
            strJSON += "{" + strName + "}, ";
        }
        strJSON = strJSON.substring(0, strJSON.length() - 2);
        strJSON += "]}";

        return strJSON;
    }
}
