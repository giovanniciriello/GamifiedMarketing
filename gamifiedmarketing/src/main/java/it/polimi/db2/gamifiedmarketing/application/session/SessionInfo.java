package it.polimi.db2.gamifiedmarketing.application.session;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//Create a class-based proxy (uses CGLIB).
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SessionInfo {

    private User user;

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        if (user == null) {
            user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return user;
    }
}