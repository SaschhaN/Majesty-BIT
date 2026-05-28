package fhnw.IT_Project.Majesty_BIT.model.domain;

public enum CardType {
    MILLER(1, "Miller"),
    BREWER(2, "Brewer"),
    WITCH(4, "Witch"),
    GUARD(3, "Guard"),
    KNIGHT(3, "Knight"),
    INNKEEPER(2, "Innkeeper"),
    NOBLE(5, "Noble");

    private final int pointValue;
    private final String displayName;

    CardType(int pointValue, String displayName) {
        this.pointValue = pointValue;
        this.displayName = displayName;
    }

    public int getPointValue() { return pointValue; }
    public String getDisplayName() { return displayName; }
}