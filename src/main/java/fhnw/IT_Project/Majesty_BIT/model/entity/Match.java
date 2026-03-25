package fhnw.IT_Project.Majesty_BIT.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "matches")
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime datePlayed;

    public Match() {
        this.datePlayed = LocalDateTime.now();
    }
}