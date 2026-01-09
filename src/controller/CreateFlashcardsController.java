package controller;

import dao.FlashcardDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.CreateFlashcards;
import view.Studycards2;
import view.Dashtwo;
import model.UserModel;

import javax.swing.*;

public class CreateFlashcardsController {
    private final CreateFlashcards cfView;
    private final Studycards2 studyPage;
    private final Dashtwo dash;
    private final UserModel currentUser;
    private final int currentDeckId;
    private final String currentDeckName;

    // ✅ Constructor
    public CreateFlashcardsController(CreateFlashcards cfView, Studycards2 studyPage,
                                      Dashtwo dash, UserModel currentUser,
                                      int currentDeckId, String currentDeckName) {
        this.cfView = cfView;
        this.studyPage = studyPage;
        this.dash = dash;
        this.currentUser = currentUser;
        this.currentDeckId = currentDeckId;
        this.currentDeckName = currentDeckName;

        // ✅ wire actions
        cfView.HomeButtonListener(new HomeListener());
        cfView.GoBackButtonListener(new GoBackListener());
        cfView.SaveButtonListener(new SaveListener());
    }

    public void open() {
        cfView.setVisible(true);
    }

    public void close() {
        cfView.dispose();
    }

    // ✅ Non-static inner classes so they can access instance fields
    private class HomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();                  // close CreateFlashcards
            studyPage.setVisible(true); // show original Studycards2
        }
    }

    private class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();                  // close CreateFlashcards
            studyPage.setVisible(true); // show original Studycards2
        }
    }

    private class SaveListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String front = cfView.getFrontTextArea().getText().trim();
        String back = cfView.getBackTextArea().getText().trim();

        if (front.isEmpty() || back.isEmpty()) {
            JOptionPane.showMessageDialog(cfView, "Please enter both front and back text.");
            return;
        }

        try {
            FlashcardDao dao = new FlashcardDao();
            int newCardId = dao.addFlashcard(currentDeckId, front, back, currentUser.getUserId());

            if (newCardId != -1) {
                studyPage.updateCardCount(currentDeckId, currentUser.getUserId());
                JOptionPane.showMessageDialog(cfView, "Flashcard saved!");
            } else {
                JOptionPane.showMessageDialog(cfView, "Error saving flashcard.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cfView, "Error saving flashcard: " + ex.getMessage());
            return;
        }

        cfView.getFrontTextArea().setText("");
        cfView.getBackTextArea().setText("");
    }
}
}