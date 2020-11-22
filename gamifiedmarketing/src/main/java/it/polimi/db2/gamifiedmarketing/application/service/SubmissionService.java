package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Submission> getAllSubmissionOfTheDay() {
//        return submissionRepository.getAllSubmissionOfTheDay();
        Product productOfTheDay = productRepository.findByDate(new Date());
        List<Submission> subs = submissionRepository.findByProductId(productOfTheDay.getId());
        return subs;
    }
}
