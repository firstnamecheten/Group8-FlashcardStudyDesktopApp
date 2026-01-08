package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import model.FlashcardModel;

public class FlashcardDao {
    private final MySqlConnection mysql = new MySqlConnection();

    // Optional: verify deck belongs to user (defensive check)
    private boolean deckExistsForUser(int deckId, int userId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT 1 FROM decks WHERE deck_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deckId);
            pstmt.setInt(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlashcardDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

    // Save a flashcard and return its generated card_id
    public int addFlashcard(int deckId, String front, String back, int userId) {
        // Optional guard: ensure deck belongs to user
        if (!deckExistsForUser(deckId, userId)) {
            Logger.getLogger(FlashcardDao.class.getName())
                  .log(Level.WARNING, "Deck {0} does not belong to user {1}", new Object[]{deckId, userId});
            return -1;
        }

        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO flashcards (deck_id, user_id, front_text, back_text) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, deckId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, front);
            pstmt.setString(4, back);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlashcardDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return -1;
    }

    // Count flashcards in a deck for a user
    public int getFlashcardCount(int deckId, int userId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT COUNT(*) FROM flashcards WHERE deck_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deckId);
            pstmt.setInt(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlashcardDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    // Get all flashcards for a deck
    public List<FlashcardModel> getFlashcardsByDeck(int deckId, int userId) {
        List<FlashcardModel> flashcards = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT card_id, deck_id, user_id, front_text, back_text FROM flashcards WHERE deck_id = ? AND user_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deckId);
            pstmt.setInt(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FlashcardModel card = new FlashcardModel(
                        rs.getInt("card_id"),
                        rs.getInt("deck_id"),
                        rs.getInt("user_id"),
                        rs.getString("front_text"),
                        rs.getString("back_text")
                    );
                    flashcards.add(card);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlashcardDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return flashcards;
    }

    public boolean deleteFlashcard(int cardId, int userId) {
        Connection conn = mysql.openConnection();
        String sql = "DELETE FROM flashcards WHERE card_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FlashcardDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
}
