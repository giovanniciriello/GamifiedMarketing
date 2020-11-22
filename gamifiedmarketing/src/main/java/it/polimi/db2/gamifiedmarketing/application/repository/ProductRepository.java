package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByDate(LocalDate date);

    @Transactional
    Integer deleteByDate(LocalDate date);
}
