package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;

import java.util.Date;
import java.util.List;

public interface AdminService {

    void deleteProductByDate(Date date);

    void addProduct(Product product);

    void getVisualQuestionnaire();

    List<Product> provaGet();
}
