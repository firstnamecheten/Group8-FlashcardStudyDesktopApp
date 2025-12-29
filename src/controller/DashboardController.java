/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.deck;
import model.UserModel;
import view.Dashboard;
import view.NewDeckDialog;
import view.UserBasedFlashcardOwnership;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import java.awt.EventQueue;  // or use javax.swing.SwingUtilities
import controller.LoginController;  // for logout
import view.Login;  // for restarting login
public class DashboardController {
    private Dashboard view;
    private UserModel currentUser;
    private List<deck> decks;
    private int nextDeckId = 1;

    public DashboardController(Dashboard view, UserModel currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        this.decks = new ArrayList<>();
        loadDecksFromFile();
        refreshDeckDisplay();
    }

    // Called when user clicks "Create"
    public void handleCreateDeck() {
        NewDeckDialog dialog = new NewDeckDialog(view, true, currentUser);
        dialog.setLocationRelativeTo(view);
        dialog.setVisible(true);

        // Assuming your NewDeckDialog has a getDeckName() or similar
        String deckName = dialog.getDeckName(); // You'll need to add this getter
      
        if (deckName != null && !deckName.trim().isEmpty()) {
            deck newDeck = new deck(nextDeckId++, deckName.trim());
            decks.add(newDeck);
            saveDeckToFile(newDeck);
            refreshDeckDisplay();
        }
    }

    private void refreshDeckDisplay() {
        view.clearDeckDisplay();
        for (deck deck : decks) {
            view.addDeckButton(deck.getName(), deck.getId());
        }
    }

    public void openDeck(int deckId, String deckName) {
        Studycards2 studyPage = new Studycards2(deckId, deckName, currentUser);
        studyPage.setVisible(true);
        view.setVisible(false); // Optional: hide dashboard
    }

    public void openAccountPage() {
        UserBasedFlashcardOwnership page = new UserBasedFlashcardOwnership(currentUser, view);
        page.setVisible(true);
        view.setVisible(false);
    }
    
    public void openStudyHistory() {
        // Show message or open history window
        javax.swing.JOptionPane.showMessageDialog(view, 
            "Study History feature coming soon will be done by Bipin!", 
            "Study History", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public void logout() {
        int confirm = JOptionPane.showConfirmDialog(view, 
        "Are you sure you want to log out?", 
        "Logout", 
        JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            view.dispose();
            SwingUtilities.invokeLater(() -> {
                Login loginView = new Login();
                LoginController controller = new LoginController(loginView);
                controller.open();
            });
    }
    }

    public void toggleDarkMode() {
        view.toggleDarkMode(); // Delegate to view for UI changes only
    }

    // === File Persistence ===
    private void saveDeckToFile(deck deck) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("decks.txt", true)))) {
            out.println(deck.getId() + "," + deck.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error saving deck.");
        }
    }

    private void loadDecksFromFile() {
        File file = new File("decks.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    decks.add(new deck(id, name));
                    maxId = Math.max(maxId, id);
                }
            }
            nextDeckId = maxId + 1;
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Error loading decks.");
        }
    }
}
