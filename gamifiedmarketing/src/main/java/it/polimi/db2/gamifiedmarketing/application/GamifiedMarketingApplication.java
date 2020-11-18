package it.polimi.db2.gamifiedmarketing.application;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Question;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

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
        User user1 = User.builder().firstName("Matteo").lastName("Giordano").email("matteo@email.com").password("pwd").isAdmin(false).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User user2 = User.builder().firstName("Andrea").lastName("Bovo").email("andrea@email.com").password("pwd").isAdmin(false).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User user3 = User.builder().firstName("Giovanni").lastName("Ciriello").email("giovanni@email.com").password("pwd").isAdmin(false).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // --- Admin --- \\
        User admin1 = User.builder().firstName("Admin").lastName("1").email("admin1@email.com").password("pwd").isAdmin(true).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User admin2 = User.builder().firstName("Admin").lastName("2").email("admin2@email.com").password("pwd").isAdmin(true).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();

        // --- Products --- \\
        Product product1 = Product.builder().name("First").description("The first product").imageUrl("Url1").date(LocalDate.of(2020, Month.NOVEMBER, 17)).questions(new ArrayList<>()).submissions(new ArrayList<>()).build();
        admin1.addProduct(product1);

        Product product2 = Product.builder().name("Second").description("The second product").imageUrl("Url2").date(LocalDate.of(2020, Month.NOVEMBER, 18)).questions(new ArrayList<>()).submissions(new ArrayList<>()).build();
        admin2.addProduct(product2);

        Product product3 = Product.builder().name("Third").description("The third product").imageUrl("Url3").date(LocalDate.of(2021, Month.JANUARY, 1)).questions(new ArrayList<>()).submissions(new ArrayList<>()).build();
        admin2.addProduct(product3);

        userRepository.save(admin1);
        userRepository.save(admin2);

        // --- Questions --- \\
        Question question1 = Question.builder().title("Do you like it?").build();
    }
}
