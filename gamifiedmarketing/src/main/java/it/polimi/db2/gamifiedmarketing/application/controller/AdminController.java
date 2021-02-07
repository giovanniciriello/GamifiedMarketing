package it.polimi.db2.gamifiedmarketing.application.controller;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.requestModels.ProductRequest;
import it.polimi.db2.gamifiedmarketing.application.entity.requestModels.ViewResponse;
import it.polimi.db2.gamifiedmarketing.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/create")
    @ResponseBody
    public ResponseEntity<ViewResponse<Product>> addProduct(@RequestBody ProductRequest productRequest) {
        ViewResponse response = productService.addProduct(productRequest);
        if (response.isValid) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/product/create")
    public String getCreationPage(){
        return "create-product";
    }

    @GetMapping(value = {"/product/search", "/product/search/{date}"})
    public String getSearchPage(Model model, @PathVariable(required = false) String date){
        model.addAttribute("product", productService.getProductByDate(date != null ? date : LocalDate.now().toString()));
        return "search-product";
    }

    @DeleteMapping(value = "/product/delete/{date}")
    @ResponseBody
    public ResponseEntity<ViewResponse> deleteProductByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ViewResponse response = productService.deleteProductByDate(date);
        if (response.isValid) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
