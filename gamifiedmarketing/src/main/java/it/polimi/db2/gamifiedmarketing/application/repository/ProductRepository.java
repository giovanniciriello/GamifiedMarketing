package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;

import java.util.Date;
import java.util.List;

public interface ProductRepository{

    Product getProductByDate(Date date);

    List<Product> provaGet();
}
