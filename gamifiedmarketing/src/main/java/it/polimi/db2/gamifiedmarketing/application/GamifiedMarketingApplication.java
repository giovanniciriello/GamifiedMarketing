package it.polimi.db2.gamifiedmarketing.application;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class GamifiedMarketingApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(GamifiedMarketingApplication.class, args);
    }

    @PostConstruct
    public void init() {

        /*
         * DB initialization for testing
         */

        // --- Users --- \\
        User user1 = User.builder().firstName("Matteo").lastName("Giordano").email("matteo@email.com").password("pwd").isAdmin(false).build();
        User user2 = User.builder().firstName("Andrea").lastName("Bovo").email("andrea@email.com").password("pwd").isAdmin(false).build();
        User user3 = User.builder().firstName("Giovanni").lastName("Ciriello").email("giovanni@email.com").password("pwd").isAdmin(false).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // --- Admin --- \\\
        User admin1 = User.builder().firstName("Admin").lastName("1").email("admin1@email.com").password("pwd").isAdmin(true).build();
        User admin2 = User.builder().firstName("Admin").lastName("2").email("admin2@email.com").password("pwd").isAdmin(true).build();
        userRepository.save(admin1);
        userRepository.save(admin2);

        // --- Products --- \\\
        Product product1 = Product.builder().name("First").description("The first product").imageUrl("Url1").date(LocalDate.of(2020, Month.NOVEMBER, 17)).admin(admin1).build();
        Product product2 = Product.builder().name("Second").description("The second product").imageUrl("Url2").date(LocalDate.of(2020, Month.NOVEMBER, 18)).admin(admin2).build();
        Product product3 = Product.builder().name("Third").description("The third product").imageUrl("Url3").date(LocalDate.of(2021, Month.JANUARY, 1)).admin(admin2).build();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

}
