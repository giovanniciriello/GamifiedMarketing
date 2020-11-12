package it.polimi.db2.gamifiedmarketing.repository;

import it.polimi.db2.gamifiedmarketing.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserById(Integer id);
}
