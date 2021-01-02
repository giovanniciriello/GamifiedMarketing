package it.polimi.db2.gamifiedmarketing.application.repository;

import it.polimi.db2.gamifiedmarketing.application.entity.BadWord;
import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.time.LocalDate;

@Repository
public interface BadWordRepository extends CrudRepository<BadWord, Integer> {
    //@Sql(scripts = { "triggers.sql" })
    //@Transactional
    //void runTriggers();
}
