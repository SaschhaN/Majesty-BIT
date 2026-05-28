package fhnw.IT_Project.Majesty_BIT.service;

import fhnw.IT_Project.Majesty_BIT.model.entity.User;
import fhnw.IT_Project.Majesty_BIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Simple hash — good enough for a student project (use BCrypt in production)
    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    // Register a new user. Returns error message or null if successful.
    public String register(String username, String password) {
        if (username == null || username.trim().isEmpty()) return "Username cannot be empty.";
        if (password == null || password.length() < 3)    return "Password must be at least 3 characters.";
        if (userRepository.findByUsername(username).isPresent()) return "Username already taken.";

        userRepository.save(new User(username.trim(), hashPassword(password)));
        return null; // null = success
    }

    // Login check. Returns the User if credentials are correct, null otherwise.
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(u -> u.getPasswordHash().equals(hashPassword(password)))
            .orElse(null);
    }

    // Top 10 players by wins — for the leaderboard screen
    public List<Map<String, Object>> getLeaderboard() {
        List<User> top = userRepository.findTop10ByOrderByTotalWinsDesc();
        List<Map<String, Object>> result = new ArrayList<>();
        for (User u : top) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("username", u.getUsername());
            row.put("wins", u.getTotalWins());
            row.put("games", u.getTotalGames());
            // Win rate as percentage, avoid divide-by-zero
            int rate = u.getTotalGames() > 0 ? (u.getTotalWins() * 100 / u.getTotalGames()) : 0;
            row.put("winRate", rate);
            result.add(row);
        }
        return result;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
