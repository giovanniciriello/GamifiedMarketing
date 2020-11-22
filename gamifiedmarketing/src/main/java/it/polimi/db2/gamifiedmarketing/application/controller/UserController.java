package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
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

    @PutMapping("/submission/{id}/submit")
    @ResponseBody
    public ViewResponse submitSubmission(@PathVariable Integer id, @RequestBody Submission submission) {
      /*   TODO Check if RequestBody properly map the Put HTTP request body to submission. If yes
       *    then fix the relationships and save(submission). If no, manually find the submission from the id
       *    and manally add all the new fields and then save(submission)
       */
        return submissionService.submitSubmission(id, submission);
    }
}
