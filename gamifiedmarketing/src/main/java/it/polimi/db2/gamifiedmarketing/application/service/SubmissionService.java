package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

    @Autowired
    private UserRepository userRepository;
}
