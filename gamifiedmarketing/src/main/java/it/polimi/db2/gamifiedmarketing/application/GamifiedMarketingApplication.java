package it.polimi.db2.gamifiedmarketing.application;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class GamifiedMarketingApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(GamifiedMarketingApplication.class, args);
    }

    @PostConstruct
    public void init() {

        /*
         * DB initialization for testing
         */

        // --- Users --- \\
        User user1 = User.builder().firstName("Matteo").lastName("Giordano").email("matteo@email.com").password("pwd").role(UserRole.CUSTOMER).build();
        User user2 = User.builder().firstName("Andrea").lastName("Bovo").email("andrea@email.com").password("pwd").role(UserRole.CUSTOMER).build();
        User user3 = User.builder().firstName("Giovanni").lastName("Ciriello").email("giovanni@email.com").password("pwd").role(UserRole.CUSTOMER).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

}
