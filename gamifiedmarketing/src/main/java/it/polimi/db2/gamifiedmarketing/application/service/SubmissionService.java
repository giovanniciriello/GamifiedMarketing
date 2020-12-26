package it.polimi.db2.gamifiedmarketing.application.service;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import it.polimi.db2.gamifiedmarketing.application.entity.*;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.helpers.ResponseJSON;
import it.polimi.db2.gamifiedmarketing.application.entity.helpers.SubmissionJSON;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.QuestionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import it.polimi.db2.gamifiedmarketing.application.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
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
    private SessionInfo sessionInfo;

    public List<Submission> getAllSubmissionOfTheDay() {
        return submissionRepository.getAllSubmissionOfTheDay(LocalDate.now(), Sort.by(Sort.Direction.DESC, "points"));
    }

    public ViewResponse logUserCancel(Integer product_id) {
        /*  Guards:
         *      --> If a user is not logged in --> return error "You seems to not be logged in!"
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
            Submission submission = submissionRepository.findByUserAndProduct(sessionUser, product);
            if (submission != null) {
                throw new Exception("You have yet a submission on that product!");
            }

            Submission log = Submission.builder().user(sessionUser).product(product).submissionStatus(SubStatus.CANCELED).build();
            submissionRepository.save(log);
            return new ViewResponse(true, log, null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse submitSubmission(Integer product_id, SubmissionJSON json) {
        /*  Guards:
         *      --> If a user is not logged in --> return error "You seems to not be logged in!"
         *      --> If the product_id is not valid --> return error "The product seems to not exists!"
         *      --> If the product is not of today --> return error "You cannot operate on product different from the one of today!"
         *      --> If user has yet submitted on that product --> return error "You have yet a submission on that product!"
         *      --> If use has not answered to all mandatory questions --> return error "All marketing questions are mandatory!"
         */
        try {
            // TODO Check on offensive words !!

            // System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

            // Check if user is logged in
            if (sessionInfo.getCurrentUser() == null) {
                throw new Exception("You seems to not be logged in!");
            }
            User sessionUser = sessionInfo.getCurrentUser();

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
            Submission submission = submissionRepository.findByUserAndProduct(sessionUser, product);
            if (submission != null) {
                throw new Exception("You have yet a submission on that product!");
            }

            // Check if user answered to all mandatory questions
            if (json.getResponses().size() == product.getQuestions().size()) {
                throw new Exception("All marketing questions are mandatory!");
            }

            Submission submit = Submission.builder()
                    .age(json.getAge())
                    .expertiseLevel(json.getExpertiseLevel())
                    .sex(json.getSex())
                    .submissionStatus(SubStatus.CONFIRMED)
                    .responses(new ArrayList<>())
                    .build();
            sessionUser.addSubmission(submit);
            submit.setProduct(product);

            for (ResponseJSON response : json.getResponses()) {
                Integer questionId = response.getQuestion_id();
                String responseBody = response.getBody();

                Optional<Question> questionMaybe = questionRepository.findById(questionId);
                try {
                    // Check if question is existing (array tampered with)
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

            userRepository.save(sessionUser);

            return new ViewResponse(true, submit, null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }
}
