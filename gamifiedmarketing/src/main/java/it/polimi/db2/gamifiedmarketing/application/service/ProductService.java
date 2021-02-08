package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Question;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.entity.requestModels.ProductRequest;
import it.polimi.db2.gamifiedmarketing.application.entity.requestModels.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import it.polimi.db2.gamifiedmarketing.application.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SessionInfo sessionInfo;

    public Product findProductOfTheDay(LocalDate date){
        return productRepository.findByDate(date);
    }

    /* Admin methods */

    public ViewResponse deleteProductByDate(LocalDate date) {
        /*  Guards:
         *      --> If the product is present or future it cannot be deleted --> return error "You cannot delete a product of the future"
         */
        try {

            //  Check if product is in the past
            if (date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now())) {
                throw new Exception("You cannot delete a product of the future");
            }

            productRepository.deleteByDate(date);
            return new ViewResponse(true, null);

        } catch(Exception e){

            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse<Product> addProduct(ProductRequest productRequest) {
        /*  Guards:
         *      --> If the product has a past date --> return error "You cannot add a product with a past date"
         *      --> If a product already exists for that date --> return error "A stored product has already the same date. Change the date"
         *      --> If no questions are created --> return error "You cannot add a product without any questions"
         */
        try{

            // Check if product is in the present or future
            if (productRequest.date.isBefore(LocalDate.now())) {
                throw new Exception("You cannot add a product with a past date");
            }

            // Check if a product already exists for that date
            if (productRepository.findByDate(productRequest.date) != null) {
                throw new Exception("A stored product has already the same date. Change the date");
            }

            // Check if there are at least one question when creating product
            if (productRequest.questions.isEmpty() || productRequest.questions == null) {
                throw new Exception("You cannot add a product without any questions");
            }

            User user = userRepository.findById(sessionInfo.getCurrentUser().getId()).get();
            //List<Question> questions = new ArrayList<>();
            Product product = Product.builder().name(productRequest.name).description(productRequest.description).imageUrl(productRequest.image_url).date(productRequest.date).questions(new ArrayList<>()).build();
            for(int i = 0; i< productRequest.questions.size(); i++) {
                Question question = Question.builder()
                        .title(productRequest.questions.get(i).title)
                        .subtitle(productRequest.questions.get(i).subtitle)
                        .build();
                product.addQuestion(question);
            }

            product.setAdmin(user);
            productRepository.save(product);
            return new ViewResponse(true, product.getId(), null);

        } catch(Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public Product getProductByDate(String date) {

        LocalDate localDate = LocalDate.parse(date);

        return productRepository.findByDate(localDate);
    }
}
