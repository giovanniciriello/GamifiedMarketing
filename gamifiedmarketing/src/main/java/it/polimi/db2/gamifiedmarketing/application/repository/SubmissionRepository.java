package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Integer> {

//    @Query("SELECT S.id, S.age, S.created_at, S.expertise_level, S.sex, S.submission_status, S.updated_at, S.product_id, S.user_id\n" +
//            "FROM gamified_db2.submissions AS S JOIN gamified_db2.products as P ON S.product_id = P.id\n" +
//            "WHERE P.date = CURDATE()")
//    public List<Submission> getAllSubmissionOfTheDay();

    List<Submission> findByProductId(Integer id);
}
