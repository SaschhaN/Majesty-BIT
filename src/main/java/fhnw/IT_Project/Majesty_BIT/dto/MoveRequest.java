package fhnw.IT_Project.Majesty_BIT.dto;

public class MoveRequest {
    private String username;
    private int displayIndex;

    public MoveRequest() {}
    public MoveRequest(String username, int displayIndex) {
        this.username = username;
        this.displayIndex = displayIndex;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getDisplayIndex() { return displayIndex; }
    public void setDisplayIndex(int displayIndex) { this.displayIndex = displayIndex; }
}
