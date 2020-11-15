package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @DeleteMapping("/submission/{id}/cancel")
    public void deleteSubmission(@PathVariable Integer id) {
        submissionRepository.deleteById(id);
    }
}
