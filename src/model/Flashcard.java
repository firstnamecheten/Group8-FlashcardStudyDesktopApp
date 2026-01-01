package model;

public class Flashcard {
    private int flashcardId;
    private String question;
    private String answer;
    private String category;
    private String difficulty;
    private boolean isReviewed;
    private int userId;
    
    public Flashcard() {}
    
    public Flashcard(int flashcardId, String question, String answer, String category, String difficulty) {
        this.flashcardId = flashcardId;
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.difficulty = difficulty;
        this.isReviewed = false;
        this.userId = 1;
    }
    
    // Getters and Setters
    public int getFlashcardId() { return flashcardId; }
    public void setFlashcardId(int flashcardId) { this.flashcardId = flashcardId; }
    
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    
    public boolean isReviewed() { return isReviewed; }
    public void setReviewed(boolean reviewed) { isReviewed = reviewed; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    @Override
    public String toString() {
        return "Flashcard [id=" + flashcardId + ", question=" + question.substring(0, Math.min(20, question.length())) + "...]";
    }
}