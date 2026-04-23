package fhnw.IT_Project.Majesty_BIT;

import fhnw.IT_Project.Majesty_BIT.model.domain.*;
import fhnw.IT_Project.Majesty_BIT.dto.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TestMajestyBitApplication {

    // Test 1: Game starts with 5 cards in display
    @Test
    void centerDisplayHasFiveCards() {
        GameState game = new GameState("T1", List.of("Alice", "Bob"), List.of(false, false), 0);
        assertEquals(5, game.getCenterDisplay().size());
    }

    // Test 2: Player receives card after drafting
    @Test
    void playerReceivesCard() {
        GameState game = new GameState("T2", List.of("Alice", "Bob"), List.of(false, false), 0);
        CardType first = game.getCenterDisplay().get(0);
        CardType taken = game.takeCardFromDisplay(0);
        assertEquals(first, taken);
        game.getCurrentPlayer().addCard(taken);
        assertEquals(1, game.getCurrentPlayer().getCardCount(first));
    }

    // Test 3: Score calculation — Noble(5) + Miller(1) = 6
    @Test
    void scoreCalculation() {
        PlayerState p = new PlayerState("Alice", false);
        p.addCard(CardType.NOBLE);
        p.addCard(CardType.MILLER);
        assertEquals(6, p.calculateScore());
    }

    // Test 4: Wrong turn is detected correctly
    @Test
    void wrongTurnDetected() {
        GameState game = new GameState("T4", List.of("Alice", "Bob"), List.of(false, false), 0);
        assertNotEquals("Bob", game.getCurrentPlayer().getUsername());
    }

    // Test 5: Winner message is built correctly
    @Test
    void winnerMessage() {
        GameState game = new GameState("T5", List.of("Alice", "Bob"), List.of(false, false), 0);
        game.getCurrentPlayer().addCard(CardType.NOBLE);
        game.setFinished(true);
        GameStateResponse r = GameStateResponse.from(game);
        assertTrue(r.getWinnerMessage().contains("Alice"));
    }

    // Test 6: Tie is detected
    @Test
    void tieDetected() {
        GameState game = new GameState("T6", List.of("Alice", "Bob"), List.of(false, false), 0);
        game.getPlayers().get(0).addCard(CardType.MILLER);
        game.getPlayers().get(1).addCard(CardType.MILLER);
        game.setFinished(true);
        GameStateResponse r = GameStateResponse.from(game);
        assertTrue(r.getWinnerMessage().toLowerCase().contains("tie"));
    }

    // Test 7: AI flag is stored correctly
    @Test
    void aiPlayerFlagWorks() {
        GameState game = new GameState("T7", List.of("Alice", "Bot"), List.of(false, true), 0);
        assertFalse(game.getPlayers().get(0).isAI());
        assertTrue(game.getPlayers().get(1).isAI());
    }

    // Test 8: AI picks the highest value card
    @Test
    void aiPicksBestCard() {
        GameState game = new GameState("T8", List.of("Alice", "Bot"), List.of(false, true), 0);
        int bestIdx = game.getAIBestMove();
        int bestVal = game.getCenterDisplay().get(bestIdx).getPointValue();
        for (CardType c : game.getCenterDisplay()) {
            assertTrue(bestVal >= c.getPointValue());
        }
    }

    // Test 9: Chat messages are stored
    @Test
    void chatWorks() {
        GameState game = new GameState("T9", List.of("Alice", "Bob"), List.of(false, false), 0);
        game.addChatMessage("Alice", "Hello!");
        assertEquals(1, game.getChatMessages().size());
        assertEquals("Alice", game.getChatMessages().get(0).getSender());
    }

    // Test 10: Max turns end condition works
    @Test
    void maxTurnsEndCondition() {
        GameState game = new GameState("T10", List.of("Alice", "Bob"), List.of(false, false), 1);
        game.nextTurn(); // turnCount becomes 1
        assertTrue(game.checkEndCondition());
    }
}
