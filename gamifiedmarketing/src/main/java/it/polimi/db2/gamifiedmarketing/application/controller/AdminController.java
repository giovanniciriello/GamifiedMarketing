package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController("/admin")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    @RequestMapping(value = "product")
    public ViewResponse<Product> addProduct(@RequestBody Product product) {
       return adminService.addProduct(product);
    }

    @GetMapping
    @RequestMapping(value = "submission/{date}")
    public ViewResponse<List<Submission>> getVisualQuestionnaire(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return adminService.getVisualQuestionnaire(date);
    }

    @RequestMapping(value = "product/{date}", method = RequestMethod.DELETE)
    public ViewResponse deleteProductByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return adminService.deleteProductByDate(date);
    }
}
