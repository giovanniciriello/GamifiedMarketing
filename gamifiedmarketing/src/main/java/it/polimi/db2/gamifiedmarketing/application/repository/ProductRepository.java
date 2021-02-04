package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Transactional
    Product findByDate(LocalDate date);

    @Transactional
    Integer deleteByDate(LocalDate date);

    //@Query("SELECT P FROM Product AS P WHERE P.date = :date")
    // Product getProductByDate(@Param("date") LocalDate date);

}
