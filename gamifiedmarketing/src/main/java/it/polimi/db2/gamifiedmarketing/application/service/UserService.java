package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public ViewResponse registerUser(String fn, String ln, String email, String pwd, String reinsertedPwd) {
        /*  Guards:
         *      --> If there are some of field missing --> return error "Some field is missing!"
         *      --> If email already exists --> return error "This email already exists, please use another!"
         *      --> If passwords do not matches --> return error "Passwords are different!"
         */
        try {

            // Check if some field are missing
            if (fn == null || ln == null || email == null || pwd == null || reinsertedPwd == null) {
                throw new Exception("Some field is missing!");
            }

            // Check if email already exists
            if (userRepository.findByEmail(email) != null) {
                throw new Exception("This email already exists, please use another!");
            }

            // Check if passwords do not match
            if (!pwd.equals(reinsertedPwd)) {
                throw new Exception("Passwords are different!");
            }

            User user = User.builder()
                    .firstName(fn)
                    .lastName(ln)
                    .email(email)
                    .role(UserRole.USER)
                    .password(new BCryptPasswordEncoder().encode("pwd"))
                    .productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();

            userRepository.save(user);

            return new ViewResponse(true, user, null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

}
