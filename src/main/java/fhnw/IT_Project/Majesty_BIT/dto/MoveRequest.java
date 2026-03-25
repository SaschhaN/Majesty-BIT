package fhnw.IT_Project.Majesty_BIT.dto;

import lombok.Data;

@Data
public class MoveRequest {
    private String username; // Who is making the move?
    private int displayIndex; // Which card from the center display (0-5) are they taking?

}