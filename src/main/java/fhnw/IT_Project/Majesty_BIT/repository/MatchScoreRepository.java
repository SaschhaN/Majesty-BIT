package fhnw.IT_Project.Majesty_BIT.repository;

import fhnw.IT_Project.Majesty_BIT.model.entity.MatchScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchScoreRepository extends JpaRepository<MatchScore, Long> {

    List<MatchScore> findByUserIdOrderByMatchDatePlayedDesc(Long userId);
}