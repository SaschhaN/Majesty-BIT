package fhnw.IT_Project.Majesty_BIT.dto;

import java.util.List;

// Sent by the client when creating a new game session
public class CreateGameRequest {

    private List<String> playerNames;
    private List<Boolean> aiFlags;   // true = that player slot is an AI
    private int maxTurns;            // 0 = play until deck is empty

    public CreateGameRequest() {}

    public List<String> getPlayerNames() { return playerNames; }
    public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }
    public List<Boolean> getAiFlags() { return aiFlags; }
    public void setAiFlags(List<Boolean> aiFlags) { this.aiFlags = aiFlags; }
    public int getMaxTurns() { return maxTurns; }
    public void setMaxTurns(int maxTurns) { this.maxTurns = maxTurns; }
}
