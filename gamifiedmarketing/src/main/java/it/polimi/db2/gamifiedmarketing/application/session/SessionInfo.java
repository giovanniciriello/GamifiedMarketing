package it.polimi.db2.gamifiedmarketing.application.session;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// @Scope("session")
public class SessionInfo {

    private User user;

    public User getCurrentUser() {
        if (user == null) {
            // Uncomment this line (and delete the following) when Spring Security is implemented in this project
            // user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = User.builder().build();
        }
        return user;
    }
}