package fhnw.IT_Project.Majesty_BIT.model.domain;

import java.util.*;

public class PlayerState {

    private String username;
    private int coins;
    private boolean isAI; // true = computer player
    private Map<CardType, Integer> kingdomCards;

    public PlayerState(String username, boolean isAI) {
        this.username = username;
        this.isAI = isAI;
        this.coins = 0;
        this.kingdomCards = new EnumMap<>(CardType.class);
        for (CardType type : CardType.values()) {
            kingdomCards.put(type, 0);
        }
    }

    public void addCard(CardType card) {
        kingdomCards.put(card, kingdomCards.get(card) + 1);
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getCardCount(CardType card) {
        return kingdomCards.getOrDefault(card, 0);
    }

    // A-side scoring: each card is worth its flat point value times count
    public int calculateScore() {
        int score = 0;
        for (Map.Entry<CardType, Integer> entry : kingdomCards.entrySet()) {
            score += entry.getKey().getPointValue() * entry.getValue();
        }
        return score;
    }

    public List<CardType> getAllCards() {
        List<CardType> all = new ArrayList<>();
        for (Map.Entry<CardType, Integer> entry : kingdomCards.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) all.add(entry.getKey());
        }
        return all;
    }

    public String getUsername() { return username; }
    public int getCoins() { return coins; }
    public boolean isAI() { return isAI; }
    public Map<CardType, Integer> getKingdomCards() { return kingdomCards; }
}
