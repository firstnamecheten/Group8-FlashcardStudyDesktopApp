package controller;

import dao.FlashcardDao;
import view.Dashtwo;
import view.Studycards2;
import view.CreateFlashcards;
import model.UserModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import view.FlipAndSwipeNavigation;

public class Studycards2Controller {
    private final Studycards2 studyPage;
    private final Dashtwo dash;
    private final UserModel currentUser;
    private int currentDeckId;
    private String currentDeckName;

    // ✅ Constructor
    public Studycards2Controller(Studycards2 studyPage, Dashtwo dash, UserModel user) {
        this.studyPage = studyPage;
        this.dash = dash;
        this.currentUser = user;

        wireActions();
    }

    private void wireActions() {
        studyPage.addAddCardsButtonListener(e -> openAddCardsDialog());
        studyPage.StudyCardsButtonListener(e -> startStudying());
        studyPage.HomeButtonListener(e -> goHome());
        studyPage.NextButtonListener(e -> goHome()); // ✅ Next also goes back to Dashtwo
    }

    public void open() {
        refreshCardCount(); // ✅ always refresh count when opening
        studyPage.setVisible(true);
    }

    public void close() {
        studyPage.dispose();
    }

    public void loadDeck(int deckId, String deckName) {
        this.currentDeckId = deckId;
        this.currentDeckName = deckName;

        // ✅ update UI via view
        studyPage.getDeckNameField().setText("Deck: " + deckName);

        // ✅ refresh count from DB
        refreshCardCount();
    }

    // === Actions ===
    private void openAddCardsDialog() {
        CreateFlashcards cfView = new CreateFlashcards(studyPage, currentDeckName, currentUser, currentDeckId);
        close();
        CreateFlashcardsController cfController =
            new CreateFlashcardsController(cfView, studyPage, dash, currentUser, currentDeckId, currentDeckName);
        cfController.open();
    }

    private void startStudying() {
    JOptionPane.showMessageDialog(studyPage,
        "Starting study session for deck: " + currentDeckName);

    // ✅ Use no-arg constructor for the view
    FlipAndSwipeNavigation flipView = new FlipAndSwipeNavigation();

    // Close current page
    close();

    // ✅ Pass arguments exactly as controller expects
    FlipAndSwipeNavigationController flipController =
        new FlipAndSwipeNavigationController(
            flipView,
            studyPage,
            dash,
            currentDeckId,                 // deckId
            currentUser.getUserId(),       // userId
            currentDeckName                // deckName
        );

    flipController.open();
}
    private void goHome() {
        close();
        dash.setVisible(true); // ✅ return to Dashtwo
    }

    // ✅ Helper method to refresh count from DB
    private void refreshCardCount() {
        if (currentDeckId > 0) {
            FlashcardDao dao = new FlashcardDao();
            int count = dao.getFlashcardCount(currentDeckId, currentUser.getUserId());
            studyPage.getNumberOfCardsCreatedField().setText(String.valueOf(count));
        }
    }
}