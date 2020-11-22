package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProductRepository productRepository;

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

    public ViewResponse submitSubmission(Integer id, Submission submission) {
        try {
            // TODO Check on offensive words !!

            // TODO Here the body of the previous TODO
            return new ViewResponse(true, submission, null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }
}
