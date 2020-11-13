package it.polimi.db2.gamifiedmarketing.repository;

import it.polimi.db2.gamifiedmarketing.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
