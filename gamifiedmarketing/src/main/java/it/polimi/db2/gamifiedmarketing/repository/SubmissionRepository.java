package it.polimi.db2.gamifiedmarketing.repository;

import it.polimi.db2.gamifiedmarketing.entity.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Integer> {
}
