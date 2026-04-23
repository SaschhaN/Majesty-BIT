package fhnw.IT_Project.Majesty_BIT.service;

import fhnw.IT_Project.Majesty_BIT.dto.*;
import fhnw.IT_Project.Majesty_BIT.model.domain.*;
import fhnw.IT_Project.Majesty_BIT.model.entity.*;
import fhnw.IT_Project.Majesty_BIT.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameEngineService {

    // All live games stored in memory
    private final Map<String, GameState> activeGames = new ConcurrentHashMap<>();

    @Autowired private MatchRepository matchRepository;
    @Autowired private MatchScoreRepository matchScoreRepository;
    @Autowired private UserService userService;

    // ── Create Game ──────────────────────────────────
    public String createGame(CreateGameRequest req) {
        String gameId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        int maxTurns = req.getMaxTurns();
        GameState game = new GameState(gameId, req.getPlayerNames(), req.getAiFlags(), maxTurns);
        activeGames.put(gameId, game);

        // If the first player is AI, make its move immediately
        processAITurnsIfNeeded(game);
        return gameId;
    }

    // ── Get State (polling) ──────────────────────────
    public GameStateResponse getGameState(String gameId) {
        return GameStateResponse.from(getGameOrThrow(gameId));
    }

    // ── Process Move ─────────────────────────────────
    public GameStateResponse processMove(String gameId, MoveRequest move) {
        GameState state = getGameOrThrow(gameId);

        if (state.isFinished()) {
            throw new IllegalStateException("Game is already finished.");
        }

        PlayerState active = state.getCurrentPlayer();

        // Don't allow human moves if it's an AI turn
        if (active.isAI()) {
            throw new IllegalStateException("It is the AI's turn — please wait.");
        }

        if (!active.getUsername().equals(move.getUsername())) {
            throw new IllegalStateException("It is not your turn! Waiting for: " + active.getUsername());
        }

        int index = move.getDisplayIndex();
        if (index < 0 || index >= state.getCenterDisplay().size()) {
            throw new IllegalArgumentException("Invalid card index: " + index);
        }

        // Apply the move
        applyMove(state, active, index);

        // Handle AI turns that follow
        processAITurnsIfNeeded(state);

        return GameStateResponse.from(state);
    }

    // ── Chat ─────────────────────────────────────────
    public GameStateResponse sendChat(String gameId, ChatRequest req) {
        GameState state = getGameOrThrow(gameId);
        if (req.getText() == null || req.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty.");
        }
        state.addChatMessage(req.getSender(), req.getText().trim());
        return GameStateResponse.from(state);
    }

    // ── AI Logic ─────────────────────────────────────
    // Keeps making AI moves as long as the current player is an AI
    private void processAITurnsIfNeeded(GameState state) {
        while (!state.isFinished() && state.getCurrentPlayer().isAI()) {
            PlayerState aiPlayer = state.getCurrentPlayer();
            int bestIndex = state.getAIBestMove();
            // Add a chat message so players can see what the AI did
            String cardName = state.getCenterDisplay().get(bestIndex).getDisplayName();
            applyMove(state, aiPlayer, bestIndex);
            state.addChatMessage("🤖 " + aiPlayer.getUsername(), "I drafted a " + cardName + "!");
        }
    }

    // Shared move logic used by both humans and AI
    private void applyMove(GameState state, PlayerState player, int displayIndex) {
        CardType chosen = state.takeCardFromDisplay(displayIndex);
        player.addCard(chosen);

        if (state.checkEndCondition()) {
            persistResults(state);
        } else {
            state.nextTurn();
        }
    }

    // ── Persist Results ───────────────────────────────
    private void persistResults(GameState state) {
        Match match = new Match(LocalDateTime.now());
        matchRepository.save(match);

        int topScore = state.getPlayers().stream()
            .mapToInt(PlayerState::calculateScore).max().orElse(0);

        for (PlayerState ps : state.getPlayers()) {
            // Skip AI players — they don't have user accounts
            if (ps.isAI()) continue;

            int score = ps.calculateScore();
            boolean won = (score == topScore);

            User user = userService.findByUsername(ps.getUsername())
                .orElseGet(() -> {
                    userService.register(ps.getUsername(), "guest");
                    return userService.findByUsername(ps.getUsername()).orElseThrow();
                });

            user.recordGame(won);
            userService.save(user);

            matchScoreRepository.save(new MatchScore(match, user, score, won));
        }
    }

    private GameState getGameOrThrow(String gameId) {
        GameState state = activeGames.get(gameId);
        if (state == null) throw new NoSuchElementException("No game found with ID: " + gameId);
        return state;
    }
}
