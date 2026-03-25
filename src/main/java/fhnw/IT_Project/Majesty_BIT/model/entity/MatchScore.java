package fhnw.IT_Project.Majesty_BIT.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_scores")
@Data
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

    @Column(nullable = false)
    private int coinsScored;

    @Column(nullable = false)
    private boolean isWinner;

}