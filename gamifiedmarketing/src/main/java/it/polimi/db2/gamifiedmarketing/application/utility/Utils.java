package it.polimi.db2.gamifiedmarketing.application.utility;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Utils {

    @Autowired
    private static SubmissionRepository submissionRepository;

    public static boolean isUserBanned(User user) {
        return user.getBannedAt() != null;
    }

    public static boolean hasUserSubmittedOnProduct(User user, Product product) {
        Submission submission = submissionRepository.findByUserAndProduct(user, product);
        return submission != null;
    }
}
