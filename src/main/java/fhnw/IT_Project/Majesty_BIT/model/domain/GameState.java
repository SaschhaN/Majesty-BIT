package fhnw.IT_Project.Majesty_BIT.model.domain;

import lombok.Data;

import java.util.List;

@Data
public class GameState {
    private String gameId;
    private List<PlayerState> players;
    private List<CardType> centerDisplay; // The 6 available cards
    private List<CardType> drawDeck;
    private int currentPlayerIndex;
    private boolean isFinished;

    public PlayerState getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}