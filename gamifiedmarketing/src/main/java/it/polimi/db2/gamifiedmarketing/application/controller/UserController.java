package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.requestModels.SubmissionRequest;
import it.polimi.db2.gamifiedmarketing.application.entity.requestModels.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.service.ProductService;
import it.polimi.db2.gamifiedmarketing.application.service.SubmissionService;
import it.polimi.db2.gamifiedmarketing.application.service.UserService;
import it.polimi.db2.gamifiedmarketing.application.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserService userService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SessionInfo sessionInfo;

    @GetMapping("/login")
    public String getLoginPage() {
        return "../static/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "../static/registration";
    }

    @PostMapping("/register")
    public String registerUser(Model model, String firstName, String lastName, String email, String pwd, String reinsertedPwd) {
        ViewResponse response = userService.registerUser(firstName, lastName, email, pwd, reinsertedPwd);
        if (response.isValid) {
            model.addAttribute("email", email);
            model.addAttribute("pwd", pwd);
            return getLoginPage();
        } else {
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            model.addAttribute("error", response.errors.get(0));
            return getRegisterPage();
        }
    }

    @GetMapping(value = {"/", "/home"})
    public String getHomePage(Model model) {
        Product productOfTheDay = productService.findProductOfTheDay(LocalDate.now());
        model.addAttribute("product", productOfTheDay);

        // Boolean needed to avoid making clickable the button to initiate a new questionnaire if user banned or yet submitted
        model.addAttribute("submission", submissionRepository.findByUserAndProduct(sessionInfo.getCurrentUser(), productOfTheDay));
        model.addAttribute("bannedUser", sessionInfo.getCurrentUser().isBanned());

        return "home";
    }

    @GetMapping("/leaderboard")
    public String getLeaderBoardPage(Model model){
        List<Submission> subs = submissionService.getAllConfirmedSubmissionOfTheDay();
        model.addAttribute("submissions", subs);
        return "leaderboard";
    }

    @GetMapping("/submission/start")
    public String getQuestionnairePage(Model model) {
        if (sessionInfo.getCurrentUser().isBanned()) {
            return "home";
        } else {
            Product product = productService.findProductOfTheDay(LocalDate.now());
            model.addAttribute("product", product);
            return "questionnaire";
        }
    }

    @DeleteMapping("/submission/{product_id}/cancel")
    @ResponseBody
    public ResponseEntity<ViewResponse> logUserCancel(@PathVariable Integer product_id) {
        ViewResponse response = submissionService.logUserCancel(product_id);
        if (response.isValid) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/submission/{product_id}/submit")
    @ResponseBody
    public ResponseEntity<ViewResponse> submitSubmission(@PathVariable Integer product_id, @RequestBody SubmissionRequest submission) {
        ViewResponse response = submissionService.submitSubmission(product_id, submission);
        if (response.isValid) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
