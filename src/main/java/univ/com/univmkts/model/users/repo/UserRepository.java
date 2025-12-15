package univ.com.univmkts.model.users.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import univ.com.univmkts.model.users.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

    Optional<User> findByUsername(String userName);
}