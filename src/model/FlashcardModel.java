package model;

public class FlashcardModel {
    private int cardId;
    private int deckId;
    private int userId;
    private String frontText;
    private String backText;

    // Constructor
    public FlashcardModel(int cardId, int deckId, int userId, String frontText, String backText) {
        this.cardId = cardId;
        this.deckId = deckId;
        this.userId = userId;
        this.frontText = frontText;
        this.backText = backText;
    }

    // Getters
    public int getCardId() {
        return cardId;
    }

    public int getDeckId() {
        return deckId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }

    // Setters (optional if you want to modify later)
    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }
}
