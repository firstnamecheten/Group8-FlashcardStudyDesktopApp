package controller;

import view.Dashtwo;
import view.UserBasedFlashcardOwnership;
import controller.LoginController;
import controller.UserBasedFlashcardOwnershipController;
import dao.DeckDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.UserModel;
import view.Login;
import view.NewDeckDialog;
import view.Studycards2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DashtwoController {
    private final Dashtwo dash;
    private final Login log;
    private boolean isDarkMode;
    private UserModel currentUser;
    private Studycards2 studyPage;
    private List<JButton> deckButtons = new ArrayList<>();
    private JPanel deckContainer;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Studycards2Controller studyController;
  

    public DashtwoController(Dashtwo dash, Login log) {
        this.dash = dash;
        this.log = log;

        wireMenuActions();
        initDeckContainer();
        loadDecksFromStorage();
        
        dash.CreateButtonListener(new CreateListener());
        dash.DeleteDeckButtonListener(new DeleteDeckListener());
        dash.DeckOrganizationButtonListener(new DeckOrganizationListener());
    }

    public void open() { dash.setVisible(true); }
    public void close() { dash.dispose(); }

    /** Centralized wiring of menu actions */
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
        UserBasedFlashcardOwnershipController ufController = new UserBasedFlashcardOwnershipController(ufView, dash);

        // ✅ Pass user info to controller/view
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

        // ✅ Insert logout record into MySQL
        dao.UserDao userDao = new dao.UserDao();

        // You must have stored loginId when the user logged in
        int loginId = currentUser.getLoginId();   // add loginId field in UserModel
        int userId = currentUser.getUserId();
        String sessionId = "session-" + userId;   // or however you track sessions
        java.sql.Timestamp logoutTime = new java.sql.Timestamp(System.currentTimeMillis());

        userDao.insertLogoutHistory(loginId, userId, sessionId, logoutTime);

        // ✅ Switch back to login screen
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
            dash.getDarkModeMenuItem().setText(" Light mode");
        } else {
            dash.getContentPane().setBackground(new Color(240, 240, 240));
            dash.getTopPanel().setBackground(Color.WHITE);
            dash.getAccountButton().setForeground(Color.BLACK);
            dash.getAccountButton().setBackground(new Color(254, 254, 254));
            dash.getDarkModeMenuItem().setText(" Dark mode");
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
                "Study History feature coming soon will be done by Bipin!",
                "Study History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void openNewDeckDialog() {
        NewDeckDialog dialog = new NewDeckDialog(dash, true, currentUser);
        dialog.setLocationRelativeTo(dash);
        dialog.setVisible(true);
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
        deckButton.putClientProperty("deckId", deckId); // ✅ store deckId



        deckButton.addActionListener(e -> {
            dash.setVisible(false);

        // ✅ Create the study page when button is clicked
        dash.setVisible(false);
        Studycards2 studyPage = new Studycards2();
        close();
        Studycards2Controller studyController = new Studycards2Controller(studyPage, dash, currentUser);
        studyController.loadDeck(deckId, deckName);
        studyController.open();


    });



        deckContainer.add(deckButton);
        deckContainer.add(Box.createVerticalStrut(10));
        deckButtons.add(deckButton);

        deckContainer.revalidate();
        deckContainer.repaint();
    }

    private void saveDeckToStorage(int deckId, String deckName) {
        try (FileWriter fw = new FileWriter("decks.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(deckId + "," + deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDecksFromStorage() {
        File file = new File("decks.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    int deckId = Integer.parseInt(parts[0].trim());
                    String deckName = parts[1].trim();
                    addDeckButton(deckName, deckId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUser(UserModel user) {
        this.currentUser = user;
    }

    

    private class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        // ✅ open NewDeckDialog when CreateButton is clicked
        NewDeckDialog newDeckView = new NewDeckDialog(dash, true, currentUser);
        newDeckView.setLocationRelativeTo(dash);
        newDeckView.setVisible(true);
        
        // ✅ After dialog closes, check if deck was created
        if (newDeckView.isDeckCreated()) {
            String deckName = newDeckView.getCreatedDeckName();
            int deckId = newDeckView.getCreatedDeckId();

            // Add button to scroll pane
            addDeckButton(deckName, deckId);

            // Save to storage
            saveDeckToStorage(deckId, deckName);
        }


        }
    }
    
    private class DeleteDeckListener implements ActionListener {

        private int deckIdToDelete;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (deckButtons.isEmpty()) {
            JOptionPane.showMessageDialog(dash, "No decks to delete.");
            return;
        }

        // Collect deck names
        String[] deckNames = deckButtons.stream()
                .map(AbstractButton::getText)
                .toArray(String[]::new);

        // Ask user which deck to delete
        String choice = (String) JOptionPane.showInputDialog(
                dash,
                "Select a deck to delete:",
                "Delete Deck",
                JOptionPane.PLAIN_MESSAGE,
                null,
                deckNames,
                deckNames[0]
        );

        if (choice == null) return; // user cancelled

        // Find the button with that name
        JButton toRemove = null;
        for (JButton btn : deckButtons) {
        if (btn.getText().equals(choice)) {
        toRemove = btn;
        deckIdToDelete = (int) btn.getClientProperty("deckId"); // ✅ get deckId
        break;
    }
}

        if (toRemove != null) {
    deckContainer.remove(toRemove);
    deckButtons.remove(toRemove);

    deckContainer.revalidate();
    deckContainer.repaint();

    // ✅ remove from file using deckId + name
    removeDeckFromStorage(deckIdToDelete, choice);

    // ✅ Delete from MySQL
    DeckDao deckDao = new DeckDao();
    boolean deleted = deckDao.deleteDeck(deckIdToDelete, currentUser.getUserId());

    if (deleted) {
        JOptionPane.showMessageDialog(dash, "Deck deleted from database.");
    } else {
        JOptionPane.showMessageDialog(dash, "Error deleting deck from database.");
    }
}


        
    }
    }
    
    private void removeDeckFromStorage(int deckId, String deckName) {
    try {
        File inputFile = new File("decks.txt");
        File tempFile = new File("decks_temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            // Each line is "deckId,deckName"
            String[] parts = currentLine.split(",", 2);
            if (parts.length == 2) {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();

                // Skip the line if it matches the deleted deck
                if (id == deckId && name.equals(deckName)) {
                    continue;
                }
            }
            writer.write(currentLine + System.lineSeparator());
        }
        writer.close();
        reader.close();

        // Replace old file with new
        if (!inputFile.delete()) {
            System.err.println("Could not delete old decks.txt");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.err.println("Could not rename temp file to decks.txt");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
    
    
    // ✅ Reorder panels not just buttons
private class DeckOrganizationListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (deckButtons.isEmpty()) {
            JOptionPane.showMessageDialog(dash, "No decks to organize.");
            return;
        }

        // Step 1: Ask user which deck to move
        String[] deckNames = deckButtons.stream()
                .map(AbstractButton::getText)
                .toArray(String[]::new);

        String choice = (String) JOptionPane.showInputDialog(
                dash,
                "Select a deck to move:",
                "Deck Organization",
                JOptionPane.PLAIN_MESSAGE,
                null,
                deckNames,
                deckNames[0]
        );

        if (choice == null) return; // user cancelled

        // Step 2: Ask direction
        Object[] options = {"Up", "Down"};
        int direction = JOptionPane.showOptionDialog(
                dash,
                "Move deck '" + choice + "' up or down?",
                "Deck Organization",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (direction == JOptionPane.CLOSED_OPTION) return;

        // Step 3: Find the button
        JButton selectedButton = null;
        for (JButton btn : deckButtons) {
            if (btn.getText().equals(choice)) {
                selectedButton = btn;
                break;
            }
        }

        if (selectedButton == null) return;

        int index = deckButtons.indexOf(selectedButton);

        // Step 4: Move in list + container
        if (direction == 0 && index > 0) { // Up
            deckButtons.remove(index);
            deckButtons.add(index - 1, selectedButton);

            deckContainer.removeAll();
            for (JButton btn : deckButtons) {
                deckContainer.add(btn);
                deckContainer.add(Box.createVerticalStrut(10));
            }
        } else if (direction == 1 && index < deckButtons.size() - 1) { // Down
            deckButtons.remove(index);
            deckButtons.add(index + 1, selectedButton);

            deckContainer.removeAll();
            for (JButton btn : deckButtons) {
                deckContainer.add(btn);
                deckContainer.add(Box.createVerticalStrut(10));
            }
        }

        // Step 5: Refresh UI
        deckContainer.revalidate();
        deckContainer.repaint();
    }
}
}