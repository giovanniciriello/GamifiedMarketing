package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void addUserRequest(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
        userService.addUser(firstName, lastName, email, password);
        // Route to Login Page
    }

    @GetMapping("/leaderboard")
    public void goToLeaderBoard(){
        // Route to leaderboard page
    }
}
