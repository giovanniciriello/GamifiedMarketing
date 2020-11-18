package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    /*
    public void addUser(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, password);
        userRepository.save(user);
    }
     */

    public void updateUser(User user){
        userRepository.save(user);
    }

}
