package fhnw.IT_Project.Majesty_BIT.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime datePlayed;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<MatchScore> scores;

    public Match() {}
    public Match(LocalDateTime datePlayed) { this.datePlayed = datePlayed; }

    public Long getId() { return id; }
    public LocalDateTime getDatePlayed() { return datePlayed; }
    public List<MatchScore> getScores() { return scores; }
}