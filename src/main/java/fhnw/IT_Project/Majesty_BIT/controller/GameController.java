package fhnw.IT_Project.Majesty_BIT.controller;

import fhnw.IT_Project.Majesty_BIT.dto.*;
import fhnw.IT_Project.Majesty_BIT.service.GameEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameEngineService gameEngineService;

    // POST /api/games/lobby/create
    // Body: { "playerNames": ["Alice","Bot"], "aiFlags": [false, true], "maxTurns": 0 }
    @PostMapping("/lobby/create")
    public ResponseEntity<?> createGame(@RequestBody CreateGameRequest req) {
        if (req.getPlayerNames() == null || req.getPlayerNames().size() < 2) {
            return ResponseEntity.badRequest().body(Map.of("error", "Need at least 2 players."));
        }
        String gameId = gameEngineService.createGame(req);
        return ResponseEntity.ok(Map.of("gameId", gameId));
    }

    // GET /api/games/{gameId}
    // Polling endpoint — clients call this every 1.5s
    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameState(@PathVariable String gameId) {
        try {
            return ResponseEntity.ok(gameEngineService.getGameState(gameId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/games/{gameId}/move
    // Body: { "username": "Alice", "displayIndex": 2 }
    @PostMapping("/{gameId}/move")
    public ResponseEntity<?> makeMove(@PathVariable String gameId, @RequestBody MoveRequest move) {
        try {
            return ResponseEntity.ok(gameEngineService.processMove(gameId, move));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/games/{gameId}/chat
    // Body: { "sender": "Alice", "text": "Good move!" }
    @PostMapping("/{gameId}/chat")
    public ResponseEntity<?> sendChat(@PathVariable String gameId, @RequestBody ChatRequest req) {
        try {
            return ResponseEntity.ok(gameEngineService.sendChat(gameId, req));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
