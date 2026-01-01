/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class NewDeckDialogController {
    
    private void saveDeck() {
    String deckName = deck_name.getText().trim();

    if (deckName.isEmpty() || deckName.equals("Deck name")) {
        JOptionPane.showMessageDialog(this, "Please enter a valid deck name.");
        return;
    }

    try {
        UserDao dao = new UserDao();
        int deckId = dao.addDeck(currentUser.getUserId(), deckName);

        if (deckId != -1) {
            // ✅ Show success popup
            JOptionPane.showMessageDialog(this, "Deck '" + deckName + "' saved successfully!");           
            createdDeckId = deckId;
            okPressed = true;
            // ✅ Add deck button directly to dashboard
            dashboard.addDeckButton(deckName, deckId);
            dashboard.saveDeckToStorage(deckId, deckName);
            
            dispose();


            dispose(); // close dialog
        } else {
            JOptionPane.showMessageDialog(this, "Error saving deck. Please try again.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving deck: " + ex.getMessage());
    }
}

        public int getCreatedDeckId() {
        return createdDeckId;
}


}
