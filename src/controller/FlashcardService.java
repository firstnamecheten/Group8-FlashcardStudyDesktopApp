/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.FlashcardDAO;
import model.Flashcard;
import java.sql.SQLException;
import java.util.List;

public class FlashcardService {
    private final FlashcardDAO flashcardDao;
    private int currentUserId;
    private List<Flashcard> currentDeck;
    private int currentCardIndex;
    private boolean isFlipped;
    
    public FlashcardService() {
        this.flashcardDao = new FlashcardDAO();
        this.currentUserId = 1; // Default user
        this.currentCardIndex = 0;
        this.isFlipped = false;
        loadDeck();
    }
    
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
        loadDeck();
    }
    
    public int getCurrentUserId() {
        return currentUserId;
    }
    
    private void loadDeck() {
        currentDeck = flashcardDao.getAllFlashcards(currentUserId);
        if (currentDeck.isEmpty()) {
            // Add sample flashcards if empty
            addSampleFlashcards();
            currentDeck = flashcardDao.getAllFlashcards(currentUserId);
        }
        currentCardIndex = 0;
        isFlipped = false;
    }
    
    private void addSampleFlashcards() {
        try {
            Flashcard f1 = new Flashcard(0, "What is MVC pattern?", 
                "Model-View-Controller is a software design pattern", "Programming", "Easy");
            f1.setUserId(currentUserId);
            flashcardDao.addFlashcard(f1);
            
            Flashcard f2 = new Flashcard(0, "What is JDBC?", 
                "Java Database Connectivity API for connecting Java to databases", "Programming", "Medium");
            f2.setUserId(currentUserId);
            flashcardDao.addFlashcard(f2);
        } catch (SQLException e) {
            System.err.println("Error adding sample flashcards: " + e.getMessage());
        }
    }
    
    public Flashcard getCurrentCard() {
        if (currentDeck.isEmpty() || currentCardIndex < 0 || currentCardIndex >= currentDeck.size()) {
            return null;
        }
        return currentDeck.get(currentCardIndex);
    }
    
    public String getCurrentCardText() {
        Flashcard card = getCurrentCard();
        if (card == null) return "No cards available";
        
        return isFlipped ? card.getAnswer() : card.getQuestion();
    }
    
    public String getCurrentSide() {
        return isFlipped ? "Answer" : "Question";
    }
    
    public void flipCurrentCard() {
        isFlipped = !isFlipped;
    }
    
    public boolean nextCard() {
        if (currentDeck.isEmpty()) return false;
        
        if (currentCardIndex < currentDeck.size() - 1) {
            currentCardIndex++;
            isFlipped = false;
            return true;
        }
        return false;
    }
    
    public boolean previousCard() {
        if (currentDeck.isEmpty()) return false;
        
        if (currentCardIndex > 0) {
            currentCardIndex--;
            isFlipped = false;
            return true;
        }
        return false;
    }
    
    public void markCurrentAsReviewed() {
        Flashcard card = getCurrentCard();
        if (card != null) {
            flashcardDao.markAsReviewed(card.getFlashcardId(), currentUserId);
            card.setReviewed(true);
        }
    }
    
    public int getCurrentCardNumber() {
        return currentCardIndex + 1;
    }
    
    public int getTotalCards() {
        return currentDeck.size();
    }
    
    public int getReviewedCount() {
        return flashcardDao.getReviewedCount(currentUserId);
    }
    
    public int calculateScore() {
        int total = getTotalCards();
        int reviewed = getReviewedCount();
        if (total == 0) return 0;
        return (int) ((reviewed / (double) total) * 100);
    }
    
    public String getCurrentCategory() {
        Flashcard card = getCurrentCard();
        return card != null ? card.getCategory() : "N/A";
    }
    
    public String getCurrentDifficulty() {
        Flashcard card = getCurrentCard();
        return card != null ? card.getDifficulty() : "N/A";
    }
    
    public boolean addNewFlashcard(String question, String answer, String category, String difficulty) {
        try {
            Flashcard newCard = new Flashcard(0, question, answer, category, difficulty);
            newCard.setUserId(currentUserId);
            boolean success = flashcardDao.addFlashcard(newCard);
            if (success) {
                loadDeck(); // Reload deck
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding flashcard: " + e.getMessage());
        }
        return false;
    }
}