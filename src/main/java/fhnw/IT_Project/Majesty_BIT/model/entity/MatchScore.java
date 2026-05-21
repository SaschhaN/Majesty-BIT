package fhnw.IT_Project.Majesty_BIT.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_scores")
public class MatchScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int coinsScored;
    private boolean isWinner;

    public MatchScore() {}

    public MatchScore(Match match, User user, int coinsScored, boolean isWinner) {
        this.match = match;
        this.user = user;
        this.coinsScored = coinsScored;
        this.isWinner = isWinner;
    }

    public Long getId() { return id; }
    public Match getMatch() { return match; }
    public User getUser() { return user; }
    public int getCoinsScored() { return coinsScored; }
    public boolean isWinner() { return isWinner; }
}