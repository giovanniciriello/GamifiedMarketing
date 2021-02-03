package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.views.AddProductRequest;
import it.polimi.db2.gamifiedmarketing.application.entity.views.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.service.AdminService;
import it.polimi.db2.gamifiedmarketing.application.service.ProductService;
import it.polimi.db2.gamifiedmarketing.application.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/product/create")
    @ResponseBody
    public ResponseEntity<ViewResponse<Product>> addProduct(@RequestBody AddProductRequest addProductRequest) {
        ViewResponse response = adminService.addProduct(addProductRequest);
        if(response.isValid){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/product/create")
    public String getCreationPage(Model model){
        return "create-product";
    }


    @GetMapping(value = {"/product/search", "/product/search/{date}"})
    public String getSearchPage(Model model, @PathVariable(required = false) String date){
        model.addAttribute("product", adminService.getProductByDate(date));
        return "search-product";
    }



    /*
    @GetMapping
    @RequestMapping(value = "submission/{date}")
    public ViewResponse<List<Submission>> getVisualQuestionnaire(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return adminService.getVisualQuestionnaire(date);
    }
    */

    @DeleteMapping(value = "product/{date}")
    @ResponseBody
    public ResponseEntity<ViewResponse> deleteProductByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ViewResponse response = adminService.deleteProductByDate(date);
        if(response.isValid){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}
