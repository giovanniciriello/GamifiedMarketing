package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.helpers.SubmissionJSON;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.service.ProductService;
import it.polimi.db2.gamifiedmarketing.application.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {

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

    @GetMapping("/leaderboard")
    public String getLeaderBoardPage(Model model){
        List<Submission> subs = submissionService.getAllSubmissionOfTheDay();
        model.addAttribute("submissions", subs);
        return "leaderboard";
    }

    @GetMapping("/submission/start")
    public String getQuestionnairePage(Model model) {
        // TODO From session (Spring Security) get the User and see if banned. If yes go to ban page, if not do the following
        Product product = productService.findProductOfTheDay(LocalDate.now());
        model.addAttribute("product", product);
        return "questionnaire";
    }

    @DeleteMapping("/submission/{product_id}/cancel")
    @ResponseBody
    public ViewResponse logUserCancel(@PathVariable Integer product_id) {
        return submissionService.logUserCancel(product_id);
    }

    @PostMapping("/submission/{product_id}/submit")
    @ResponseBody
    public ViewResponse submitSubmission(@PathVariable Integer product_id, @RequestBody SubmissionJSON submission) {
        return submissionService.submitSubmission(product_id, submission);
    }
}
