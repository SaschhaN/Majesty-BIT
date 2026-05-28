package fhnw.IT_Project.Majesty_BIT.model.domain;

import java.util.*;

public class GameState {

    private String gameId;
    private List<CardType> centerDisplay;
    private List<CardType> drawDeck;
    private int currentPlayerIndex;
    private boolean isFinished;
    private List<PlayerState> players;
    private List<ChatMessage> chatMessages;

    // Game options
    private int maxTurns;        // 0 = no turn limit (end when deck empty)
    private int turnCount;       // how many total turns have been played

    public GameState(String gameId, List<String> playerNames, List<Boolean> aiFlags, int maxTurns) {
        this.gameId = gameId;
        this.isFinished = false;
        this.currentPlayerIndex = 0;
        this.turnCount = 0;
        this.maxTurns = maxTurns;
        this.players = new ArrayList<>();
        this.drawDeck = new ArrayList<>();
        this.centerDisplay = new ArrayList<>();
        this.chatMessages = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            boolean isAI = (aiFlags != null && i < aiFlags.size()) && aiFlags.get(i);
            players.add(new PlayerState(playerNames.get(i), isAI));
        }

        initializeDeck();
        refillDisplay();
    }

    private void initializeDeck() {
        int[] counts = {10, 9, 8, 8, 7, 7, 6};
        CardType[] types = CardType.values();
        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < counts[i]; j++) {
                drawDeck.add(types[i]);
            }
        }
        Collections.shuffle(drawDeck);
    }

    public void refillDisplay() {
        int displaySize = 5;
        while (centerDisplay.size() < displaySize && !drawDeck.isEmpty()) {
            centerDisplay.add(drawDeck.remove(0));
        }
    }

    public void nextTurn() {
        turnCount++;
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public PlayerState getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean checkEndCondition() {
        // End if deck and display are empty
        boolean deckEmpty = drawDeck.isEmpty() && centerDisplay.isEmpty();
        // End if max turns reached (0 means no limit)
        boolean turnsReached = maxTurns > 0 && turnCount >= maxTurns;
        if (deckEmpty || turnsReached) {
            this.isFinished = true;
        }
        return this.isFinished;
    }

    public CardType takeCardFromDisplay(int displayIndex) {
        CardType taken = centerDisplay.remove(displayIndex);
        refillDisplay();
        return taken;
    }

    public void addChatMessage(String sender, String text) {
        chatMessages.add(new ChatMessage(sender, text));
        // Keep only last 50 messages in memory
        if (chatMessages.size() > 50) chatMessages.remove(0);
    }

    // AI picks the highest-value card available
    public int getAIBestMove() {
        int bestIndex = 0;
        int bestValue = -1;
        for (int i = 0; i < centerDisplay.size(); i++) {
            int val = centerDisplay.get(i).getPointValue();
            if (val > bestValue) {
                bestValue = val;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    public String getGameId() { return gameId; }
    public List<CardType> getCenterDisplay() { return centerDisplay; }
    public List<CardType> getDrawDeck() { return drawDeck; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public boolean isFinished() { return isFinished; }
    public void setFinished(boolean finished) { isFinished = finished; }
    public List<PlayerState> getPlayers() { return players; }
    public List<ChatMessage> getChatMessages() { return chatMessages; }
    public int getTurnCount() { return turnCount; }
    public int getMaxTurns() { return maxTurns; }
}
