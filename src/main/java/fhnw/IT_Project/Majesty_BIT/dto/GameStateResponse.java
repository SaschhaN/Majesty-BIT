package fhnw.IT_Project.Majesty_BIT.dto;

import fhnw.IT_Project.Majesty_BIT.model.domain.*;
import java.util.*;

public class GameStateResponse {

    private String gameId;
    private String activePlayerName;
    private boolean isFinished;
    private boolean activePlayerIsAI;
    private List<CardType> centerDisplay;
    private List<PlayerSummary> playerSummaries;
    private String winnerMessage;
    private List<ChatMessageDto> chatMessages;
    private int turnCount;
    private int maxTurns;

    // Nested DTO for each player's public state
    public static class PlayerSummary {
        public String username;
        public int score;
        public boolean isAI;
        public Map<String, Integer> cards;

        public PlayerSummary(PlayerState ps) {
            this.username = ps.getUsername();
            this.score = ps.calculateScore();
            this.isAI = ps.isAI();
            this.cards = new LinkedHashMap<>();
            for (Map.Entry<CardType, Integer> e : ps.getKingdomCards().entrySet()) {
                cards.put(e.getKey().getDisplayName(), e.getValue());
            }
        }
    }

    // Nested DTO for chat messages
    public static class ChatMessageDto {
        public String sender;
        public String text;
        public String timestamp;

        public ChatMessageDto(ChatMessage cm) {
            this.sender = cm.getSender();
            this.text = cm.getText();
            this.timestamp = cm.getTimestamp();
        }
    }

    public static GameStateResponse from(GameState state) {
        GameStateResponse r = new GameStateResponse();
        r.gameId = state.getGameId();
        r.isFinished = state.isFinished();
        r.centerDisplay = new ArrayList<>(state.getCenterDisplay());
        r.activePlayerName = state.getCurrentPlayer().getUsername();
        r.activePlayerIsAI = state.getCurrentPlayer().isAI();
        r.turnCount = state.getTurnCount();
        r.maxTurns = state.getMaxTurns();

        r.playerSummaries = new ArrayList<>();
        for (PlayerState ps : state.getPlayers()) {
            r.playerSummaries.add(new PlayerSummary(ps));
        }

        r.chatMessages = new ArrayList<>();
        for (ChatMessage cm : state.getChatMessages()) {
            r.chatMessages.add(new ChatMessageDto(cm));
        }

        if (state.isFinished()) {
            r.winnerMessage = buildWinnerMessage(state);
        }
        return r;
    }

    private static String buildWinnerMessage(GameState state) {
        int topScore = -1;
        List<String> winners = new ArrayList<>();
        for (PlayerState ps : state.getPlayers()) {
            int score = ps.calculateScore();
            if (score > topScore) {
                topScore = score;
                winners.clear();
                winners.add(ps.getUsername());
            } else if (score == topScore) {
                winners.add(ps.getUsername());
            }
        }
        if (winners.size() > 1) {
            return "It's a tie between: " + String.join(", ", winners) + " with " + topScore + " points!";
        }
        return winners.get(0) + " wins with " + topScore + " points!";
    }

    public String getGameId() { return gameId; }
    public String getActivePlayerName() { return activePlayerName; }
    public boolean isFinished() { return isFinished; }
    public boolean isActivePlayerIsAI() { return activePlayerIsAI; }
    public List<CardType> getCenterDisplay() { return centerDisplay; }
    public List<PlayerSummary> getPlayerSummaries() { return playerSummaries; }
    public String getWinnerMessage() { return winnerMessage; }
    public List<ChatMessageDto> getChatMessages() { return chatMessages; }
    public int getTurnCount() { return turnCount; }
    public int getMaxTurns() { return maxTurns; }
}
