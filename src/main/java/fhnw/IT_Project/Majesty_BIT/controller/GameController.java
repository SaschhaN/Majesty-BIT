package fhnw.IT_Project.Majesty_BIT.controller;

import fhnw.IT_Project.Majesty_BIT.dto.GameStateResponse;
import fhnw.IT_Project.Majesty_BIT.dto.MoveRequest;
import fhnw.IT_Project.Majesty_BIT.model.domain.GameState;
import fhnw.IT_Project.Majesty_BIT.service.GameEngineService;
import fhnw.IT_Project.Majesty_BIT.model.entity.*;
import fhnw.IT_Project.Majesty_BIT.repository.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    // Spring Boot injects service logic here
    private final GameEngineService gameService;

    public GameController(GameEngineService gameService) {
        this.gameService = gameService;
    }

    /**
     * Endpoint to handle a player's move.
     * URL: POST http://localhost:8080/api/games/{gameId}/move
     */
    @PostMapping("/{gameId}/move")
    public ResponseEntity<GameStateResponse> makeMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest request) {

        try {
            // 1. Passes the move to the server logic
            GameState updatedState = gameService.processMove(
                    gameId,
                    request.getUsername(),
                    request.getDisplayIndex()
            );

            // 2. Converts internal GameState to a safe GameStateResponse DTO
            GameStateResponse response = convertToDto(updatedState);

            // 3. Sends HTTP 200 OK with the new game state
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // If the move is invalid (e.g., not their turn), send HTTP 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    // Controller for starting a game
    @PostMapping("/start")
    public ResponseEntity<GameStateResponse> startGame(@RequestBody List<String> playerNames) {
        // 1. Creates the game logic
        GameState newState = gameService.createGame(playerNames);

        // 2. Returns the initial state so the frontend knows what cards are available
        return ResponseEntity.ok(convertToDto(newState));
    }

    // Helper method to map GameState to GameStateResponse
    private GameStateResponse convertToDto(GameState state) {
        GameStateResponse dto = new GameStateResponse();
        dto.setGameId(state.getGameId());
        // ... rest of fields need to be mapped
        dto.setCenterDisplay(state.getCenterDisplay());

        // Safety check: ensures there are players before getting the username
        if (state.getPlayers() != null && !state.getPlayers().isEmpty()) {
            dto.setCurrentPlayerUsername(state.getCurrentPlayer().getUsername());
        }
        return dto;
    }
}