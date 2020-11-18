package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.Submission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Integer> {
    @Query("SELECT S\n"
            + "FROM Submission AS S JOIN Product as P ON S.user.id = P.id\n"
            + "WHERE P.date = :date")
    public List<Submission> getAllSubmissionOfTheDay(@Param("date") Date date);

    //public List<Submission> findAllByCreatedAt_Date(LocalDate localDate);
}
