package fhnw.IT_Project.Majesty_BIT.model.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// A single chat message sent by a player during a game
public class ChatMessage {

    private String sender;
    private String text;
    private String timestamp;

    public ChatMessage(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getSender() { return sender; }
    public String getText() { return text; }
    public String getTimestamp() { return timestamp; }
}
