package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Question;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import it.polimi.db2.gamifiedmarketing.application.entity.views.AddProductRequest;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import it.polimi.db2.gamifiedmarketing.application.session.SessionInfo;
import it.polimi.db2.gamifiedmarketing.application.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SessionInfo sessionInfo;

    public ViewResponse deleteProductByDate(LocalDate date) {
        try{
            if (sessionInfo.getCurrentUser() == null) {
                throw new Exception("You seems to not be logged in!");
            }
            if (!sessionInfo.getCurrentUser().getRole().equals(UserRole.ADMIN)) {
                throw new Exception("You cannot delete a product if you are not admin");
            }
            if (date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now())) {
                throw new Exception("You cannot delete a product of the future");
            }

            productRepository.deleteByDate(date);
            return new ViewResponse(true, null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse<Product> addProduct(AddProductRequest addProductRequest) {
        try{
            if (sessionInfo.getCurrentUser() == null) {
                throw new Exception("You seems to not be logged in!");
            }
            if (!sessionInfo.getCurrentUser().getRole().equals(UserRole.ADMIN)) {
                throw new Exception("You cannot delete a product if you are not admin");
            }
            if (addProductRequest.date.isBefore(LocalDate.now())) {
                throw new Exception("You cannot add a product with a past date");
            }
            if (productRepository.findByDate(LocalDate.now()) != null) {
                throw new Exception("A stored product has already the same date. change the date");
            }
            if (addProductRequest.questions.isEmpty() || addProductRequest.questions == null) {
                throw new Exception("You cannot add a product without any questions");
            }

            Optional<User> user = userRepository.findById(sessionInfo.getCurrentUser().getId());
            List<Question> questions = new ArrayList<>();
            for(int i=0; i<addProductRequest.questions.size(); i++) {
                Question question = Question.builder()
                        .title(addProductRequest.questions.get(i).title)
                        .subtitle(addProductRequest.questions.get(i).subtitle)
                        .build();
                questions.add(question);
            }

            Product product = Product.builder().name(addProductRequest.name).description(addProductRequest.description).imageUrl(addProductRequest.image_url).date(addProductRequest.date).questions(questions).build();
            product.setAdmin(user.get());
            Product _return = productRepository.save(product);
            return new ViewResponse(true, _return.getId(), null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public Product getProductByDate(String date) {

        if(date == null){
            return null;
        }

        LocalDate localDate = LocalDate.parse(date);

        return productRepository.findByDate(localDate);
    }
}
