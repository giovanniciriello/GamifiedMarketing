package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import it.polimi.db2.gamifiedmarketing.application.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Integer> {

    @Query("SELECT S\n"
            + "FROM Submission AS S JOIN Product as P ON S.product.id = P.id\n"
            + "WHERE P.date = :date")
    List<Submission> getAllSubmissionOfTheDay(@Param("date") LocalDate date);

    @Query("SELECT S\n"
            + "FROM Submission AS S JOIN Product as P ON S.product.id = P.id\n"
            + "WHERE P.date = :date")
    List<Submission> getAllSubmissionOfTheDay(@Param("date") LocalDate date, Sort sort);

    List<Submission> findByProductId(Integer id);

    Submission findByUserAndProduct(User user, Product product);
}
