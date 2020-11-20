package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.service.ProductService;
import it.polimi.db2.gamifiedmarketing.application.service.SubmissionService;
import it.polimi.db2.gamifiedmarketing.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/home")
    public String getHomePage(Model model) {
        Product productOfTheDay = productService.findProductOfTheDay(LocalDate.now());
        model.addAttribute("product", productOfTheDay);
        return "home";
    }

    @GetMapping("/questionnaire")
    public String getQuestionnairePage() {
        return "questionnaire";
    }


    @GetMapping("/leaderboard")
    public String getLeaderBoardPage(Model model){
        List<Submission> subs = submissionService.getAllSubmissionOfTheDay();
        model.addAttribute("submissions", subs);
        return "leaderboard";
    }
}
