package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO for managing Decks
 * Handles adding and deleting decks in the database.
 */
public class DeckDao {
    private final MySqlConnection mysql = new MySqlConnection();

    // Save a new deck and return its generated deck_id
    public int addDeck(int userId, String deckName) {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO decks (user_id, deck_name) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, deckName);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // ✅ return the new deck_id
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeckDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return -1; // only if something failed
    }

    // Delete a deck (flashcards will be deleted automatically via ON DELETE CASCADE in flashcards table)
    public boolean deleteDeck(int deckId, int userId) {
        Connection conn = mysql.openConnection();
        String sql = "DELETE FROM decks WHERE deck_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deckId);
            pstmt.setInt(2, userId);
            int rows = pstmt.executeUpdate();
            return rows > 0; // ✅ true if a deck was deleted
        } catch (SQLException ex) {
            Logger.getLogger(DeckDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
}