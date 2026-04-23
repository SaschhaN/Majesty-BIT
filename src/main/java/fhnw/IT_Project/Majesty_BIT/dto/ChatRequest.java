package fhnw.IT_Project.Majesty_BIT.dto;

public class ChatRequest {
    private String sender;
    private String text;

    public ChatRequest() {}

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
