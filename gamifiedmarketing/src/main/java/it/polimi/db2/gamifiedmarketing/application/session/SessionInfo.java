package it.polimi.db2.gamifiedmarketing.application.session;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionInfo {

    private User user;

    public User getCurrentUser() {
        if (user == null) {
            user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
}