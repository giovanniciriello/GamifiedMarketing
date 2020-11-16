package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    @Autowired
    private EntityManager em;


    @Override
    public Product getProductByDate(Date date) {
        return null;
    }

    @Override
    public List<Product> provaGet() {
        return  em.createQuery("SELECT product FROM Product as product")
                  .getResultList();
    }
}
