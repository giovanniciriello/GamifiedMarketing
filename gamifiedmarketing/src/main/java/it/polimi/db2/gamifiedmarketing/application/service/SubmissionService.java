package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<Submission> getAllSubmissionOfTheDay() {
        return submissionRepository.getAllSubmissionOfTheDay(LocalDate.now(), Sort.by(Sort.Direction.ASC, "points"));
    }

    public Submission createSubmission() {
        Product productOfTheDay = productRepository.findByDate(LocalDate.now());
        //TODO Obtain (Spring Security) the User from the session and add to the submission
        Submission submission = Submission.builder().submissionStatus(SubStatus.CREATED).product(productOfTheDay).build();
        submissionRepository.save(submission);
        return submission;
    }


    public ViewResponse deleteSubmission(Integer id) {
        try {
            Optional<Submission> submission = submissionRepository.findById(id);
            if (submission.isPresent()) {
                submission.get().setSubmissionStatus(SubStatus.CANCELED);
                submissionRepository.save(submission.get());
            } else {
                throw new NullPointerException("You cannot delete a Submission you have not created!");
            }
            return new ViewResponse(true, null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }

    public ViewResponse submitSubmission(Integer id, Submission submission) {
        try {
            // TODO Here the body of the previous TODO
            return new ViewResponse(true, submission, null);
        } catch (Exception e) {
            var errors = new ArrayList<String>();
            errors.add(e.getMessage());
            return new ViewResponse(false, errors);
        }
    }
}
