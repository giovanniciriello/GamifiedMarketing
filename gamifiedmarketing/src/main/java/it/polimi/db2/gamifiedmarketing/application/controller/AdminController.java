package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import it.polimi.db2.gamifiedmarketing.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    @RequestMapping(value = "product")
    public void addProduct(@PathVariable Product product) {
        adminService.addProduct(product);
    }

    @GetMapping
    @RequestMapping(value = "visualQuestionnaire")
    public void getVisualQuestionnaire() {
        adminService.getVisualQuestionnaire();
    }

    @RequestMapping(value = "product/{date}", method = RequestMethod.DELETE)
    public void deleteProductByDate(@PathVariable Date date) {
        adminService.deleteProductByDate(date);
    }

    /*@GetMapping
    @RequestMapping(value = "product")
    public Iterable<Product> provedGet() {
        return adminService.provaGet();
    }*/
}
