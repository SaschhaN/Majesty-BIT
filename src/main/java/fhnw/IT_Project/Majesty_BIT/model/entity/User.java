package fhnw.IT_Project.Majesty_BIT.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // "user" is a reserved keyword in PostgreSQL, so "users" is used
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    public User() {}

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
