package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Question;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Transactional
    Product findByDate(LocalDate date);

    @Transactional
    Integer deleteByDate(LocalDate date);

    //@Query("SELECT P FROM Product AS P WHERE P.date = :date")
    // Product getProductByDate(@Param("date") LocalDate date);

}
