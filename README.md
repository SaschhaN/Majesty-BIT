# ⚜️ Majesty · BIT Edition

A multiplayer card game built with **Spring Boot** (backend) and **HTML/JavaScript** (frontend) as part of the IT-Project module at FHNW.

**Group BIT** — Yilmaz Özaydin, Sascha Niederhauser, Alexander Burri  
**Supervisor** — Lukas Frey

---

## 📋 Table of Contents

- [About the Game](#about-the-game)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)

---

## 🎮 About the Game

Majesty is a card drafting game where players take turns picking cards from a shared market display and building their kingdom. Each card type is worth a different number of points. The player with the most points when the deck runs out wins.

### Card Types (A-Side)

| Card | Icon | Points |
|------|------|--------|
| Miller | 🌾 | 1 pt |
| Brewer | 🍺 | 2 pts |
| Innkeeper | 🏠 | 2 pts |
| Guard | 🛡️ | 3 pts |
| Knight | ⚔️ | 3 pts |
| Witch | 🧙 | 4 pts |
| Noble | 👑 | 5 pts |

---

## ✅ Features

### Requirements
- ✅ Client/Server implementation (Spring Boot + HTML frontend)
- ✅ Players can make moves (draft cards from the market)
- ✅ A-Side location cards implemented
- ✅ Fixed number of players (2–4)
- ✅ Winner calculation with message
- ✅ No Meeples, no split cards, no red/green back distinction
- ✅ **Login / Register / Guest** — accounts saved to database
- ✅ **Lobby** — configure player names, number of players, AI slots
- ✅ **Game options** — choose end condition (deck empty or fixed turns)
- ✅ **Database** — stores players, matches, and high scores (PostgreSQL)
- ✅ **AI / Computer Player** — AI picks the highest-value card automatically
- ✅ **Chat** — players can send messages during the game
- ✅ **Nice design** — dark medieval fantasy theme with animations
- ✅ **Language support** — English 🇬🇧 and German 🇩🇪

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 4.x |
| Database | PostgreSQL 15 (via Docker) |
| ORM | Spring Data JPA / Hibernate |
| Frontend | HTML, CSS, Vanilla JavaScript |
| Container | Docker + Docker Compose |
| DB Admin | pgAdmin 4 |

---

## 📁 Project Structure

```
majesty/
├── src/
│   ├── main/
│   │   ├── java/fhnw/IT_Project/Majesty_BIT/
│   │   │   ├── controller/
│   │   │   │   ├── GameController.java      # REST endpoints for game actions
│   │   │   │   ├── UserController.java      # Login, register, leaderboard
│   │   │   │   └── WebConfig.java           # CORS configuration
│   │   │   ├── dto/
│   │   │   │   ├── CreateGameRequest.java   # Request body for creating a game
│   │   │   │   ├── MoveRequest.java         # Request body for making a move
│   │   │   │   ├── ChatRequest.java         # Request body for chat messages
│   │   │   │   ├── LoginRequest.java        # Request body for login/register
│   │   │   │   └── GameStateResponse.java   # Response sent to clients
│   │   │   ├── model/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── CardType.java        # Enum of all card types + points
│   │   │   │   │   ├── GameState.java       # Live in-memory game state
│   │   │   │   │   ├── PlayerState.java     # Each player's cards and score
│   │   │   │   │   └── ChatMessage.java     # A single chat message
│   │   │   │   └── entity/
│   │   │   │       ├── User.java            # DB entity for player accounts
│   │   │   │       ├── Match.java           # DB entity for completed games
│   │   │   │       └── MatchScore.java      # DB entity for scores per match
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── MatchRepository.java
│   │   │   │   └── MatchScoreRepository.java
│   │   │   ├── service/
│   │   │   │   ├── GameEngineService.java   # All game logic + AI
│   │   │   │   └── UserService.java         # Login, register, leaderboard
│   │   │   └── MajestyBitApplication.java   # Spring Boot entry point
│   │   └── resources/
│   │       ├── static/
│   │       │   └── index.html               # Complete frontend (single file)
│   │       └── application.properties       # DB connection + JPA config
│   └── test/
│       └── java/fhnw/IT_Project/Majesty_BIT/
│           └── TestMajestyBitApplication.java  # 10 unit tests
├── compose.yaml                             # Docker services (PostgreSQL + pgAdmin)
├── pom.xml                                  # Maven dependencies
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Make sure you have these installed:

| Tool | Version | Download |
|------|---------|----------|
| Java JDK | 17 or 21 | https://adoptium.net |
| Docker Desktop | Latest | https://www.docker.com/products/docker-desktop |
| VS Code | Latest | https://code.visualstudio.com |

**VS Code Extensions to install:**
- Extension Pack for Java (Microsoft)
- Spring Boot Extension Pack (VMware)

---

### 1. Clone / Open the Project

Open the `majesty` folder in IDE.

---

### 2. Create `application.properties`

If it doesn't exist, create the file at:
```
src/main/resources/application.properties
```

With this content:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/majesty_realm
spring.datasource.username=majesty_user
spring.datasource.password=majesty_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

spring.application.name=Majesty-BIT
server.port=8080
```

---

### 3. Start the Database

```bash
docker compose up -d
```

This starts PostgreSQL on port `5432` and pgAdmin on port `8081`.

---

### 4. Run the Server

```bash
./mvnw spring-boot:run
```

Wait until you see:
```
Started MajestyBitApplication in X seconds
```

---

### 5. Open the Game

Go to your browser and open:
```
http://localhost:8080
```

---

### Every time you restart your Mac

```bash
# 1. Open Docker Desktop and wait for it to start
# 2. Open the project in VS Code
# 3. In the terminal:
./mvnw spring-boot:run
# 4. Open http://localhost:8080
```

---

## 🎲 How to Play

### Login Screen
- **Login** — enter username and password to play with a saved account
- **Register** — create a new account (wins are tracked in the leaderboard)
- **Guest** — play without an account (scores not saved)
- Switch language with 🇬🇧 / 🇩🇪 buttons

### Lobby Screen
- Choose number of players (2–4)
- Enter player names
- Check the 🤖 **AI** checkbox to make a player slot a computer player
- Choose end condition: deck empty (default) or fixed number of turns
- Click **Begin the Game**

### Identity Screen
- Each human player clicks their own name to identify themselves
- You can switch identity anytime using the **"Playing as X ↺"** button in the top bar

### Game Screen
- When it's **your turn**, the market cards light up — click one to draft it
- When it's **another player's turn**, pass the keyboard to them and they click their name to switch
- Use the **Chat** box on the right to send messages
- The **All Players** panel shows everyone's cards and scores in real time

### Winning
- The game ends automatically when the deck and market are both empty (or max turns reached)
- The winner screen shows final scores and the updated leaderboard

---

## 🌐 API Endpoints

### Game Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/games/lobby/create` | Create a new game |
| GET | `/api/games/{gameId}` | Get current game state (polling) |
| POST | `/api/games/{gameId}/move` | Make a move (draft a card) |
| POST | `/api/games/{gameId}/chat` | Send a chat message |

### User Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/users/register` | Register a new account |
| POST | `/api/users/login` | Login with username + password |
| GET | `/api/users/leaderboard` | Get top 10 players by wins |

### Example: Create a game
```json
POST /api/games/lobby/create
{
  "playerNames": ["Alice", "Bob", "AI-Bot"],
  "aiFlags": [false, false, true],
  "maxTurns": 0
}
```

### Example: Make a move
```json
POST /api/games/ABC12345/move
{
  "username": "Alice",
  "displayIndex": 2
}
```

---

## 🧪 Running Tests

```bash
./mvnw test
```

The test suite covers 10 cases:

| # | Test |
|---|------|
| 1 | Game starts with 5 cards in the display |
| 2 | Player receives a card after drafting |
| 3 | Score calculation is correct |
| 4 | Wrong turn is detected |
| 5 | Winner message is built correctly |
| 6 | Tie is handled correctly |
| 7 | AI player flag is stored correctly |
| 8 | AI picks the highest-value card |
| 9 | Chat messages are stored |
| 10 | Max turns end condition works |

---

## 🗄 Database (pgAdmin)

pgAdmin runs at `http://localhost:8081`

| Field | Value |
|-------|-------|
| Email | admin@admin.com |
| Password | admin |
| DB Host | majesty-db |
| DB Port | 5432 |
| DB Name | majesty_realm |
| DB User | majesty_user |
| DB Password | majesty_password |

Tables created automatically by Spring Boot:
- `users` — player accounts, wins, total games
- `matches` — completed game sessions
- `match_scores` — each player's score per match

- See all users
SELECT * FROM users;

- See all matches
SELECT * FROM matches;

- See all match scores
SELECT * FROM match_scores;


---

## 👥 Team

| Name | Responsibilities |
|------|-----------------|
| Yilmaz Özaydin | GameEngine, GameState logic, Move validation, Frontend |
| Sascha Niederhauser | Database design, Repositories, Persistence logic |
| Alexander Burri | API Integration, Networking, UI/UX |
