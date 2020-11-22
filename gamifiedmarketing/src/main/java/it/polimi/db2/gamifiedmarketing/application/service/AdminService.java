package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
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
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public ViewResponse deleteProductByDate(LocalDate date) {
        try{
            productRepository.deleteByDate(date);
            return new ViewResponse(true, null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse<Product> addProduct(Product product) {
        try{
            Optional<User> user = userRepository.findById(product.getAdmin().getId());
            product.setAdmin(user.get());
            Product _return = productRepository.save(product);
            return new ViewResponse(true, _return, null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse<List<Submission>> getVisualQuestionnaire(LocalDate date) {
        try{
            List<Submission>  _return = submissionRepository.getAllSubmissionOfTheDay(date);
            return new ViewResponse(true, _return, null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }
}
