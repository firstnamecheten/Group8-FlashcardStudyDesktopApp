package controller;

import dao.FlashcardDao;
import model.FlashcardModel;
import view.FlipAndSwipeNavigation;
import view.Studycards2;
import view.Dashtwo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FlipAndSwipeNavigationController {

    private final FlipAndSwipeNavigation flipView;
    private final Studycards2 studyPage;
    private final Dashtwo dash;
    private final int deckId;
    private final int userId;
    private final String deckName;

    private List<FlashcardModel> cards;
    private int currentIndex = 0;
    private boolean showingFront = true;


    // Constructor
    public FlipAndSwipeNavigationController(FlipAndSwipeNavigation flipView,
                                            Studycards2 studyPage,
                                            Dashtwo dash,
                                            int deckId,
                                            int userId,
                                            String deckName) {
        this.flipView = flipView;
        this.studyPage = studyPage;
        this.dash = dash;
        this.deckId = deckId;
        this.userId = userId;
        this.deckName = deckName;

        // Load cards from DB
        FlashcardDao dao = new FlashcardDao();
        this.cards = dao.getFlashcardsByDeck(deckId, userId);

        // Wire buttons
        flipView.CrosstButtonListener(new CrossListener());
        flipView.RightButtonListener(new NextListener());
        flipView.LeftButtonListener(new PrevListener());
        flipView.DeleteCardButtonListener(new DeleteListener());

        // Wire text area click for flip
        flipView.getFrontTextField().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                flipCard();
            }
        });

        // Show first card
        showCard();
    }

    public void open() {
        flipView.setVisible(true);
    }

    public void close() {
        flipView.dispose();
    }

    // === Actions ===
    private void showCard() {
    if (cards.isEmpty()) {
        flipView.getFrontTextField().setText("No cards in this deck.");
        flipView.getCardNumberField().setText("0/0");
        return;
    }

    FlashcardModel card = cards.get(currentIndex);

    // Always start with front text
    flipView.getFrontTextField().setText(card.getFrontText());

    int total = cards.size();
    int current = currentIndex + 1;

    // ✅ Show as "current/total"
    flipView.getCardNumberField().setText("  "+ current + "/" + total + " cards"+ " ");

    showingFront = true;
}

    private void flipCard() {
        if (cards.isEmpty()) return;

        FlashcardModel card = cards.get(currentIndex);

        if (showingFront) {
            flipView.getFrontTextField().setText(card.getBackText());
            showingFront = false;
        } else {
            flipView.getFrontTextField().setText(card.getFrontText());
            showingFront = true;
        }
    }


    private class CrossListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            studyPage.setVisible(true);
        }
    }

    private class NextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentIndex < cards.size() - 1) {
                currentIndex++;
                showCard();
            }
        }
    }

    private class PrevListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentIndex > 0) {
                currentIndex--;
                showCard();
            }
        }
    }
    
    private class DeleteListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (cards.isEmpty()) return;

        FlashcardModel card = cards.get(currentIndex);

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                flipView,
                "Delete this card permanently?",
                "Confirm Delete",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            FlashcardDao dao = new FlashcardDao();
            dao.deleteFlashcard(card.getCardId(), userId);

            cards.remove(currentIndex);

            if (currentIndex >= cards.size() && currentIndex > 0) {
                currentIndex--;
            }

            // ✅ Refresh FlipAndSwipeNavigation card number
            showCard();

            // ✅ Refresh Studycards2 card count
            studyPage.updateCardCount(deckId, userId);
        }
    }
}
}