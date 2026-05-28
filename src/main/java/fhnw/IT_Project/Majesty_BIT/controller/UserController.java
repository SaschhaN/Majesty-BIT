package fhnw.IT_Project.Majesty_BIT.controller;

import fhnw.IT_Project.Majesty_BIT.dto.LoginRequest;
import fhnw.IT_Project.Majesty_BIT.model.entity.User;
import fhnw.IT_Project.Majesty_BIT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/users/register
    // Body: { "username": "Alice", "password": "1234" }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest req) {
        String error = userService.register(req.getUsername(), req.getPassword());
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        return ResponseEntity.ok(Map.of("message", "Registered successfully!"));
    }

    // POST /api/users/login
    // Body: { "username": "Alice", "password": "1234" }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userService.login(req.getUsername(), req.getPassword());
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password."));
        }
        return ResponseEntity.ok(Map.of(
            "username", user.getUsername(),
            "wins", user.getTotalWins(),
            "games", user.getTotalGames()
        ));
    }

    // GET /api/users/leaderboard
    // Returns top 10 players sorted by wins
    @GetMapping("/leaderboard")
    public ResponseEntity<?> leaderboard() {
        return ResponseEntity.ok(userService.getLeaderboard());
    }
}
