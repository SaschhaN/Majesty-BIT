package fhnw.IT_Project.Majesty_BIT.model.domain;

import lombok.Data;

import java.util.EnumMap;
import java.util.Map;

@Data
public class PlayerState {
    private String username;
    private int coins;
    private Map<CardType, Integer> kingdomCards;

    public PlayerState(String username) {
        this.username = username;
        this.coins = 0;
        this.kingdomCards = new EnumMap<>(CardType.class);

        for (CardType type : CardType.values()) {
            this.kingdomCards.put(type, 0);
        }
    }

    public void addCard(CardType card) {
        this.kingdomCards.put(card, this.kingdomCards.get(card) + 1);
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getCardCount(CardType card) {
        return this.kingdomCards.getOrDefault(card, 0);
    }

}