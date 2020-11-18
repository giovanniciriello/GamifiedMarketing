package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.service.SubmissionService;
import it.polimi.db2.gamifiedmarketing.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/questionnaire")
    public String getQuestionnairePage() {
        return "questionnaire";
    }


    @GetMapping("/leaderboard")
    public String goToLeaderBoard(Model model){
        List<Submission> subs = submissionService.getAllSubmissionOfTheDay();

        // Result can be accessed in Thymeleaf pages with ${submissions}
        // TODO In this way we do not have the email of the users but only their IDs
        model.addAttribute("submissions", subs);
        return "leaderboard";
    }
}
