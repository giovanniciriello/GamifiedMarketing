package it.polimi.db2.gamifiedmarketing.application;

import it.polimi.db2.gamifiedmarketing.application.entity.*;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        User user1 = User.builder().firstName("Matteo").lastName("Giordano").email("matteo@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.USER).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User user2 = User.builder().firstName("Andrea").lastName("Bovo").email("andrea@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.USER).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User user3 = User.builder().firstName("Giovanni").lastName("Ciriello").email("giovanni@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.USER).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // --- Admins --- \\
        User admin1 = User.builder().firstName("Admin").lastName("1").email("admin1@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.ADMIN).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();
        User admin2 = User.builder().firstName("Admin").lastName("2").email("admin2@email.com").password(new BCryptPasswordEncoder().encode("pwd")).role(UserRole.ADMIN).productsCreated(new ArrayList<>()).submissions(new ArrayList<>()).build();

        // --- Products --- \\
        Product product1 = Product.builder().name("First").description("Ciao ragazzi").imageUrl("http://via.placeholder.com/500").date(LocalDate.now()).questions(new ArrayList<>()).submissions(new ArrayList<>()).build();
        admin1.addProduct(product1);

        Product product2 = Product.builder().name("Second").description("The second product").imageUrl("http://via.placeholder.com/300").date(LocalDate.of(2020, Month.NOVEMBER, 18)).questions(new ArrayList<>()).submissions(new ArrayList<>()).build();
        admin2.addProduct(product2);

        Product product3 = Product.builder().name("Third").description("The third product").imageUrl("http://via.placeholder.com/500").date(LocalDate.of(2021, Month.JANUARY, 1)).questions(new ArrayList<>()).submissions(new ArrayList<>()).build();
        admin2.addProduct(product3);

        // --- Questions --- \\
        Question question1_1 = Question.builder().title("Do you like it?").subtitle("Subtitle 1").responses(new ArrayList<>()).build();
        Question question2_1 = Question.builder().title("Would you buy it?").subtitle("Subtitle 2").responses(new ArrayList<>()).build();
        product1.addQuestion(question1_1);
        product1.addQuestion(question2_1);

        Question question1_2 = Question.builder().title("Do you like it?").subtitle("Subtitle 1").responses(new ArrayList<>()).build();
        Question question2_2 = Question.builder().title("Would you buy it?").subtitle("Subtitle 2").responses(new ArrayList<>()).build();
        product2.addQuestion(question1_2);
        product2.addQuestion(question2_2);

        Question question1_3 = Question.builder().title("Do you like it?").subtitle("Subtitle 1").responses(new ArrayList<>()).build();
        Question question2_3 = Question.builder().title("Would you buy it?").subtitle("Subtitle 2").responses(new ArrayList<>()).build();
        product3.addQuestion(question1_3);
        product3.addQuestion(question2_3);

        // Here (persisting the admin) is exampled the use case of the admin that create a new product with related questions.
        userRepository.save(admin1);
        userRepository.save(admin2);

        // --- Responses --- \\
        Response response1_1 = Response.builder().body("yes1").build();
        Response response2_1 = Response.builder().body("no1").build();
        question1_1.addResponse(response1_1);
        question2_1.addResponse(response2_1);


        Response response1_2 = Response.builder().body("yes2").build();
        Response response2_2 = Response.builder().body("no2").build();
        question1_1.addResponse(response1_2);
        question2_1.addResponse(response2_2);

        // --- Submissions --- \\
        Submission sub_user1 = Submission.builder().age(12).expertiseLevel(ExpertiseLevel.LOW).sex(Sex.MALE).submissionStatus(SubStatus.CONFIRMED).responses(new ArrayList<>()).build();
        user1.addSubmission(sub_user1);
        sub_user1.setProduct(product1);
        sub_user1.addResponse(response1_1);
        sub_user1.addResponse(response2_1);

        Submission sub_user2 = Submission.builder().age(24).expertiseLevel(ExpertiseLevel.HIGH).sex(Sex.FEMALE).submissionStatus(SubStatus.CANCELED).responses(new ArrayList<>()).build();
        user2.addSubmission(sub_user2);
        sub_user2.setProduct(product1);
        sub_user2.addResponse(response1_2);
        sub_user2.addResponse(response2_2);


        // Here (persisting the user) is exampled the use case of the user that submit/cancel a new submission with related answers.
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
