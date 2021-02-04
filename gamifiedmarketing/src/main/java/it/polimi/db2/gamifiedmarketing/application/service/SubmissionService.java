package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.*;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.helpers.ResponseJSON;
import it.polimi.db2.gamifiedmarketing.application.entity.helpers.SubmissionJSON;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.*;
import it.polimi.db2.gamifiedmarketing.application.session.SessionInfo;
import it.polimi.db2.gamifiedmarketing.application.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private BadWordRepository badWordRepository;


    @Autowired
    private SessionInfo sessionInfo;

    private final Utils utils = new Utils();

    public List<Submission> getAllSubmissionOfTheDay() {
        return submissionRepository.getAllSubmissionOfTheDay(LocalDate.now(), Sort.by(Sort.Direction.DESC, "points"));
    }

    public ViewResponse logUserCancel(Integer product_id) {
        /*  Guards:
         *      --> If a user is not logged in --> return error "You seems to not be logged in!"
         *      --> If user is banned --> return error "You cannot answer questionnaires anymore"
         *      --> If the product_id is not valid --> return error "The product seems to not exists!"
         *      --> If the product is not of today --> return error "You cannot operate on product different from the one of today!"
         *      --> If user has yet submitted on that product --> return error "You have yet a submission on that product!"
         */
        try {
            // Check if user is logged in
            if (sessionInfo.getCurrentUser() == null) {
                throw new Exception("You seems to not be logged in!");
            }
            User sessionUser = sessionInfo.getCurrentUser();

            // Check if user is banned
            if (utils.isUserBanned(sessionUser)) {
                throw new Exception("You cannot answer questionnaires anymore");
            }

            // Check if the product exists
            Optional<Product> productMaybe = productRepository.findById(product_id);
            if (productMaybe.isEmpty()) {
                throw new Exception("The product seems to not exists!");
            }
            Product product = productMaybe.get();

            // Check if the product is not of today (Here we are sure the product exists)
            if (!product.getDate().equals(LocalDate.now())) {
                throw new Exception("You cannot operate on product different from the one of today!");
            }

            // Check if user has yet submitted on that product
            if (submissionRepository.findByUserAndProduct(sessionUser, product) != null) {
                throw new Exception("You have yet a submission on that product!");
            }

            Submission log = Submission.builder().user(sessionUser).product(product).submissionStatus(SubStatus.CANCELED).build();
            submissionRepository.save(log);
            return new ViewResponse(true, log.getId(), null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse submitSubmission(Integer product_id, SubmissionJSON json) {
        /*  Guards:
         *      --> If a user is not logged in --> return error "You seems to not be logged in!"
         *      --> If user is banned --> return error "You cannot answer questionnaires anymore"
         *      --> If the product_id is not valid --> return error "The product seems to not exists!"
         *      --> If the product is not of today --> return error "You cannot operate on product different from the one of today!"
         *      --> If user has yet submitted on that product --> return error "You have yet a submission on that product!"
         *      --> If use has not answered to all mandatory questions --> return error "All marketing questions are mandatory!"
         */
        try {

            // Check if user is logged in
            if (sessionInfo.getCurrentUser() == null) {
                throw new Exception("You seems to not be logged in!");
            }
            User sessionUser = sessionInfo.getCurrentUser();

            // Check if user is banned
            if (utils.isUserBanned(sessionUser)) {
                throw new Exception("You cannot answer questionnaires anymore");
            }

            // Check if the product exists
            Optional<Product> productMaybe = productRepository.findById(product_id);
            if (productMaybe.isEmpty()) {
                throw new Exception("The product seems to not exists!");
            }
            Product product = productMaybe.get();

            // Check if the product is not of today (Here we are sure the product exists)
            if (!product.getDate().equals(LocalDate.now())) {
                throw new Exception("You cannot operate on product different from the one of today!");
            }

            // Check if user has yet submitted on that product
            if (submissionRepository.findByUserAndProduct(sessionUser, product) != null) {
                throw new Exception("You have yet a submission on that product!");
            }

            // Check if user answered to all mandatory questions
            if (json.getResponses().size() != product.getQuestions().size()) {
                throw new Exception("All marketing questions are mandatory!");
            }

            sessionUser = userRepository.findByEmail(sessionUser.getEmail());



            Submission submit = Submission.builder()
                    .age(json.getAge())
                    .expertiseLevel(json.getExpertiseLevel().getExpertiseLevel())
                    .sex(json.getSex())
                    .points(0)
                    .submissionStatus(SubStatus.CONFIRMED)
                    .responses(new ArrayList<>())
                    .build();

            Iterable<BadWord> badWords = badWordRepository.findAll();

            for (ResponseJSON response : json.getResponses()) {

                Integer questionId = response.getQuestion_id();
                String responseBody = response.getBody();

                List<String> words = Arrays.asList(responseBody.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase().split(" "));;

                for (BadWord badWord: badWords) {
                    if(words.contains(badWord.getText())){
                        sessionUser.setBannedAt(LocalDateTime.now());
                        userRepository.save(sessionUser);
                        throw new Exception("You wrote a bad word, banned!");
                    }

                }

                Optional<Question> questionMaybe = questionRepository.findById(questionId);
                try {
                    // Check if question is existing (ar raytampered with)
                    if (questionMaybe.isEmpty()) {
                        throw new Exception("You hacker!");
                    }

                    // Check if the question is related to the right product (product of the day)
                    if (!questionMaybe.get().getProduct().getDate().equals(product.getDate())) {
                        throw new Exception("You hacker!");
                    }
                } catch (Exception e) {
                    var errors = new ArrayList<String>();
                    errors.add(e.getMessage());
                    return new ViewResponse(false, errors);
                }

                Question question = questionMaybe.get();
                Response tmp = Response.builder().body(responseBody).build();
                submit.addResponse(tmp);
                question.addResponse(tmp);
            }

            submit.setProduct(product);
            sessionUser.addSubmission(submit);

            userRepository.save(sessionUser);

            return new ViewResponse(true, submit.getId(), null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }
}
