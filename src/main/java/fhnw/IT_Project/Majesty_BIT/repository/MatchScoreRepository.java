package fhnw.IT_Project.Majesty_BIT.repository;

import fhnw.IT_Project.Majesty_BIT.model.entity.MatchScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchScoreRepository extends JpaRepository<MatchScore, Long> {

    // This will handle the "High Score" requirement for a better grade
    // It will fetch the top scores across all games, ordered highest to lowest
    List<MatchScore> findTop10ByOrderByCoinsScoredDesc();

    // Find all scores for a specific user to display their personal history
    List<MatchScore> findByUserIdOrderByMatchDatePlayedDesc(Long userId);
}