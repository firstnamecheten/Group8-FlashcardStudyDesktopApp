package controller;

import view.Dashtwo;
import view.UserBasedFlashcardOwnership;
import controller.UserBasedFlashcardOwnershipController;
import dao.DeckDao;
import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.UserModel;
import view.Login;
import view.NewDeckDialog;
import view.Studycards2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashtwoController {
    private final Dashtwo dash;
    private final Login log;
    private boolean isDarkMode;
    private UserModel currentUser;

    private List<JButton> deckButtons = new ArrayList<>();
    private JPanel deckContainer;

    public DashtwoController(Dashtwo dash, Login log) {
        this.dash = dash;
        this.log = log;
        wireMenuActions();
        initDeckContainer();

        dash.CreateButtonListener(new CreateListener());
        dash.DeleteDeckButtonListener(new DeleteDeckListener());
        dash.DeckOrganizationButtonListener(new DeckOrganizationListener());
    }

    public void open() {
        loadDecksFromDatabase();   // load only decks for currentUser
        dash.setVisible(true);     // show dashboard window
    }

    public void close() { dash.dispose(); }

    /** Menu wiring **/
    private void wireMenuActions() {
        isDarkMode = false;
        if (dash.getDarkModeMenuItem() != null)
            dash.getDarkModeMenuItem().addActionListener(e -> toggleDarkMode());

        if (dash.getFontSizeMenuItem() != null)
            dash.getFontSizeMenuItem().addActionListener(e -> showFontSizeOptions());

        if (dash.getStudyHistoryMenuItem() != null)
            dash.getStudyHistoryMenuItem().addActionListener(e -> openStudyHistory());

        if (dash.getLogoutMenuItem() != null)
            dash.getLogoutMenuItem().addActionListener(e -> logout());

        if (dash.getAccountMenuItem() != null)
            dash.getAccountMenuItem().addActionListener(e -> openUserBasedFlashcardOwnership());
    }

    private void openUserBasedFlashcardOwnership() {
        dash.setVisible(false);
        UserBasedFlashcardOwnership ufView = new UserBasedFlashcardOwnership();
        UserBasedFlashcardOwnershipController ufController =
                new UserBasedFlashcardOwnershipController(ufView, dash);
        ufController.setUser(currentUser);
        ufController.open();
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            dash,
            "Are you sure you want to log out?",
            "Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            UserDao userDao = new UserDao();
            int loginId = currentUser.getLoginId();
            int userId = currentUser.getUserId();
            String sessionId = "session-" + userId;
            java.sql.Timestamp logoutTime = new java.sql.Timestamp(System.currentTimeMillis());
            userDao.insertLogoutHistory(loginId, userId, sessionId, logoutTime);

            dash.setVisible(false);
            log.setVisible(true);
        }
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            dash.getContentPane().setBackground(new Color(30, 30, 30));
            dash.getTopPanel().setBackground(new Color(40, 40, 40));
            dash.getAccountButton().setForeground(Color.WHITE);
            dash.getAccountButton().setBackground(new Color(40, 40, 40));
            dash.getDarkModeMenuItem().setText("Light mode");
        } else {
            dash.getContentPane().setBackground(new Color(240, 240, 240));
            dash.getTopPanel().setBackground(Color.WHITE);
            dash.getAccountButton().setForeground(Color.BLACK);
            dash.getAccountButton().setBackground(new Color(254, 254, 254));
            dash.getDarkModeMenuItem().setText("Dark mode");
        }
        dash.repaint();
        dash.revalidate();
    }

    private void showFontSizeOptions() {
        String[] options = {"Small", "Medium", "Large"};
        int choice = JOptionPane.showOptionDialog(
            dash,
            "Choose font size",
            "Font Size",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[1]
        );

        Font current = dash.getHomeLabel().getFont();
        switch (choice) {
            case 0 -> dash.getHomeLabel().setFont(current.deriveFont(14f));
            case 1 -> dash.getHomeLabel().setFont(current.deriveFont(18f));
            case 2 -> dash.getHomeLabel().setFont(current.deriveFont(24f));
        }
    }

    private void openStudyHistory() {
        JOptionPane.showMessageDialog(
            dash,
            "Study History feature coming soon!",
            "Study History",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void initDeckContainer() {
        deckContainer = new JPanel();
        deckContainer.setLayout(new BoxLayout(deckContainer, BoxLayout.Y_AXIS));
        dash.getScrollPane().setViewportView(deckContainer);
        dash.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dash.getScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void addDeckButton(String deckName, int deckId) {
        JButton deckButton = new JButton(deckName);
        deckButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        deckButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        deckButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        deckButton.setFocusPainted(false);
        deckButton.putClientProperty("deckId", deckId);

        deckButton.addActionListener(e -> {
            dash.setVisible(false);
            Studycards2 studyPage = new Studycards2();
            close();
            Studycards2Controller studyController =
                    new Studycards2Controller(studyPage, dash, currentUser);
            studyController.loadDeck(deckId, deckName);
            studyController.open();
        });

        deckContainer.add(deckButton);
        deckContainer.add(Box.createVerticalStrut(10));
        deckButtons.add(deckButton);

        deckContainer.revalidate();
        deckContainer.repaint();
    }

    /** Load decks only for current user **/
    private void loadDecksFromDatabase() {
        DeckDao deckDao = new DeckDao();
        List<String[]> userDecks = deckDao.getDecksByUser(currentUser.getUserId());
        deckContainer.removeAll();
        deckButtons.clear();

        if (userDecks.isEmpty()) {
            JLabel emptyLabel = new JLabel("No decks created yet. Use 'Create' to add one.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            deckContainer.add(emptyLabel);
        } else {
            for (String[] deck : userDecks) {
                int deckId = Integer.parseInt(deck[0]);
                String deckName = deck[1];
                addDeckButton(deckName, deckId);
            }
        }

        deckContainer.revalidate();
        deckContainer.repaint();
    }

    public void setCurrentUser(UserModel user) {
        this.currentUser = user;
    }

    /** Create new deck **/
    private class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NewDeckDialog newDeckView = new NewDeckDialog(dash, true, currentUser);
            newDeckView.setLocationRelativeTo(dash);
            newDeckView.setVisible(true);

            if (newDeckView.isDeckCreated()) {
                String deckName = newDeckView.getCreatedDeckName();
                int deckId = newDeckView.getCreatedDeckId();
                addDeckButton(deckName, deckId);
            }
        }
    }

    /** Delete deck **/
    private class DeleteDeckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (deckButtons.isEmpty()) {
                JOptionPane.showMessageDialog(dash, "No decks to delete.");
                return;
            }

            String[] deckNames = deckButtons.stream()
                    .map(AbstractButton::getText)
                    .toArray(String[]::new);

            String choice = (String) JOptionPane.showInputDialog(
                dash,
                "Select a deck to delete:",
                "Delete Deck",
                JOptionPane.PLAIN_MESSAGE,
                null,
                deckNames,
                deckNames[0]
            );

            if (choice == null) return;

            JButton toRemove = null;
            int deckIdToDelete = -1;
            for (JButton btn : deckButtons) {
                if (btn.getText().equals(choice)) {
                    toRemove = btn;
                    deckIdToDelete = (int) btn.getClientProperty("deckId");
                    break;
                }
            }

            if (toRemove != null) {
                deckContainer.remove(toRemove);
                deckButtons.remove(toRemove);
                deckContainer.revalidate();
                deckContainer.repaint();

                                DeckDao deckDao = new DeckDao();
                boolean deleted = deckDao.deleteDeck(deckIdToDelete, currentUser.getUserId());
                if (deleted) {
                    JOptionPane.showMessageDialog(dash, "Deck deleted.");
                } else {
                    JOptionPane.showMessageDialog(dash, "Error deleting deck.");
                }
            }
        }
    }

    /** Deck organization (placeholder for reordering/favorites) **/
    private class DeckOrganizationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                dash,
                "Deck organization feature coming soon!",
                "Deck Organization",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}