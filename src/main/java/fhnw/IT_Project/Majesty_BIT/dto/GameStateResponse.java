package fhnw.IT_Project.Majesty_BIT.dto;

import fhnw.IT_Project.Majesty_BIT.model.domain.CardType;
import lombok.Data;

import java.util.List;

@Data
public class GameStateResponse {
    private String gameId;
    private String currentPlayerUsername;
    private List<CardType> centerDisplay;
    // ... other fields the client needs to render the UI

}
