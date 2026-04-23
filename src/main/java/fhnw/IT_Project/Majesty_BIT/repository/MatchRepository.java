package fhnw.IT_Project.Majesty_BIT.repository;

import fhnw.IT_Project.Majesty_BIT.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {}
