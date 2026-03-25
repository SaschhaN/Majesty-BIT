package fhnw.IT_Project.Majesty_BIT.service;

import fhnw.IT_Project.Majesty_BIT.model.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GameEngineService {

    // For now, active games are kept in memory
    // Later, we might use Redis, but a ConcurrentHashMap is fine for now.
    private final Map<String, GameState> activeGames = new ConcurrentHashMap<>();

    public GameState processMove(String gameId, String username, int displayIndex) {
        GameState game = activeGames.get(gameId);

        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        if (game.isFinished()) {
            throw new IllegalArgumentException("Game is already finished.");
        }

        PlayerState currentPlayer = game.getCurrentPlayer();

        // 1. Validate it is actually this player's turn
        if (!currentPlayer.getUsername().equals(username)) {
            throw new IllegalArgumentException("It is not your turn!");
        }

        // 2. Validate the chosen card index
        if (displayIndex < 0 || displayIndex >= game.getCenterDisplay().size()) {
            throw new IllegalArgumentException("Invalid card selection.");
        }

        // 3. Draft the card
        CardType draftedCard = game.getCenterDisplay().remove(displayIndex);
        currentPlayer.addCard(draftedCard);

        // 4. Replenish the center display if the deck isn't empty
        if (!game.getDrawDeck().isEmpty()) {
            game.getCenterDisplay().add(game.getDrawDeck().remove(0));
        }

        // 5. Calculate Score for the drafted card (A-Side Rules)
        calculatePoints(currentPlayer, draftedCard);

        // 6. Pass the turn or end the game
        // (Assuming 12 cards per player triggers game end based on Majesty rules)
        if (isGameEndConditionMet(game)) {
            game.setFinished(true);
            // Here would be a call to another service to save the final scores to PostgreSQL
        } else {
            game.nextTurn();
        }

        return game;
    }

    // Inside src/main/java/ch/fhnw/majesty/service/GameEngineService.java

    public GameState createGame(List<String> playerUsernames) {
        GameState gameState = new GameState();
        gameState.setGameId(UUID.randomUUID().toString());

        // 1. Initialize Players
        List<PlayerState> players = playerUsernames.stream()
                .map(PlayerState::new)
                .collect(Collectors.toList());
        gameState.setPlayers(players);

        // 2. Create the Deck (Majesty distribution)
        List<CardType> deck = new ArrayList<>();
        // Example distribution (adjust numbers based on actual game rules)
        addCardsToDeck(deck, CardType.MILLER, 10);
        addCardsToDeck(deck, CardType.BREWER, 10);
        addCardsToDeck(deck, CardType.WITCH, 8);
        addCardsToDeck(deck, CardType.GUARD, 8);
        addCardsToDeck(deck, CardType.KNIGHT, 8);
        addCardsToDeck(deck, CardType.INNKEEPER, 8);
        addCardsToDeck(deck, CardType.NOBLE, 8);

        Collections.shuffle(deck);
        gameState.setDrawDeck(deck);

        // 3. Populate the Center Display (6 cards)
        List<CardType> display = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (!gameState.getDrawDeck().isEmpty()) {
                display.add(gameState.getDrawDeck().remove(0));
            }
        }
        gameState.setCenterDisplay(display);

        gameState.setCurrentPlayerIndex(0); // Player 1 starts
        gameState.setFinished(false);

        // Save to your in-memory map so processMove can find it later
        activeGames.put(gameState.getGameId(), gameState);

        return gameState;
    }

    private void addCardsToDeck(List<CardType> deck, CardType type, int count) {
        for (int i = 0; i < count; i++) {
            deck.add(type);
        }
    }

    private void calculatePoints(PlayerState player, CardType draftedCard) {
        int earnedCoins = 0;

        // Implement the A-Side location scoring logic here
        switch (draftedCard) {
            case MILLER:
                // 2 coins per Miller
                earnedCoins = player.getCardCount(CardType.MILLER) * 2;
                break;
            case BREWER:
                // 2 coins per Miller and Brewer
                earnedCoins = (player.getCardCount(CardType.MILLER) + player.getCardCount(CardType.BREWER)) * 2;
                break;
            case INNKEEPER:
                // 4 coins per Innkeeper
                earnedCoins = player.getCardCount(CardType.INNKEEPER) * 4;
                break;
            // Add the rest of the A-Side card rules (Witch, Guard, Knight, Noble)
        }

        player.addCoins(earnedCoins);
    }

    private boolean isGameEndConditionMet(GameState game) {
        // Game ends when all players have 12 cards in their kingdom
        int totalCards = 0;
        for (CardType type : CardType.values()) {
            totalCards += game.getCurrentPlayer().getCardCount(type);
        }
        return totalCards >= 12;
    }
}
