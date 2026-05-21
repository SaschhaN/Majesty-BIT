package fhnw.IT_Project.Majesty_BIT.repository;

import fhnw.IT_Project.Majesty_BIT.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // Top 10 players by wins — used for leaderboard
    List<User> findTop10ByOrderByTotalWinsDesc();
}