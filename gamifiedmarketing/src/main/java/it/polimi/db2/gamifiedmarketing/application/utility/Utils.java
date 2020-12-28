package it.polimi.db2.gamifiedmarketing.application.utility;

import it.polimi.db2.gamifiedmarketing.application.entity.User;

public class Utils {

    public boolean isUserBanned(User user) {
        return user.getBannedAt() != null;
    }
}
