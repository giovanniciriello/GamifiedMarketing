package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private ProductRepository productRepository;

    public ViewResponse deleteProductByDate(Date date) {
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
            productRepository.save(product);
            return new ViewResponse(true, product, null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse<List<Submission>> getVisualQuestionnaire(Date date) {
        try{
            //LocalDate localDate = date.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDate();
            //List<Submission> _return = submissionRepository.findAllByCreatedAt_Date(localDate);
            List<Submission> _return = submissionRepository.getAllSubmissionOfTheDay(date);
            return new ViewResponse(true, _return, null);
        }catch(Exception e){
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }
}
