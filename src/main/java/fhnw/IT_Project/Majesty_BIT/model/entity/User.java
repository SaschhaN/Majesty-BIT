package fhnw.IT_Project.Majesty_BIT.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    // Total wins across all games — shown on leaderboard
    private int totalWins;

    // Total games played
    private int totalGames;

    public User() {}

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.totalWins = 0;
        this.totalGames = 0;
    }

    public void recordGame(boolean won) {
        totalGames++;
        if (won) totalWins++;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String p) { this.passwordHash = p; }
    public int getTotalWins() { return totalWins; }
    public int getTotalGames() { return totalGames; }
}
