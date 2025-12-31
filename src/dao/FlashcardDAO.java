package dao;

import model.Flashcard;
import database.Mysqlconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashcardDAO {
    private Mysqlconnection mysql = new Mysqlconnection();
    
    public List<Flashcard> getAllFlashcards(int userId) {
        List<Flashcard> flashcards = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM flashcards WHERE user_id = ? ORDER BY flashcard_id";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Flashcard card = new Flashcard();
                card.setFlashcardId(rs.getInt("flashcard_id"));
                card.setQuestion(rs.getString("question"));
                card.setAnswer(rs.getString("answer"));
                card.setCategory(rs.getString("category"));
                card.setDifficulty(rs.getString("difficulty"));
                card.setReviewed(rs.getBoolean("is_reviewed"));
                card.setUserId(rs.getInt("user_id"));
                flashcards.add(card);
            }
        } catch (Exception e) {
            System.out.println("Error fetching flashcards: " + e);
        } finally {
            mysql.closeConnection(conn);
        }
        return flashcards;
    }
    
    public boolean addFlashcard(Flashcard flashcard) throws SQLException {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO flashcards (question, answer, category, difficulty, user_id) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, flashcard.getQuestion());
            pstm.setString(2, flashcard.getAnswer());
            pstm.setString(3, flashcard.getCategory());
            pstm.setString(4, flashcard.getDifficulty());
            pstm.setInt(5, flashcard.getUserId());
            
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    flashcard.setFlashcardId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public boolean markAsReviewed(int flashcardId, int userId) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE flashcards SET is_reviewed = TRUE WHERE flashcard_id = ? AND user_id = ?";
        
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, flashcardId);
            pstm.setInt(2, userId);
            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Error marking as reviewed: " + e);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    public int getTotalCount(int userId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT COUNT(*) FROM flashcards WHERE user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error getting total count: " + e);
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }
    
    public int getReviewedCount(int userId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT COUNT(*) FROM flashcards WHERE user_id = ? AND is_reviewed = TRUE";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error getting reviewed count: " + e);
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }
    
    public List<Flashcard> getFlashcardsByCategory(int userId, String category) {
        List<Flashcard> flashcards = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM flashcards WHERE user_id = ? AND category = ? ORDER BY flashcard_id";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Flashcard card = new Flashcard();
                card.setFlashcardId(rs.getInt("flashcard_id"));
                card.setQuestion(rs.getString("question"));
                card.setAnswer(rs.getString("answer"));
                card.setCategory(rs.getString("category"));
                card.setDifficulty(rs.getString("difficulty"));
                flashcards.add(card);
            }
        } catch (Exception e) {
            System.out.println("Error fetching by category: " + e);
        } finally {
            mysql.closeConnection(conn);
        }
        return flashcards;
    }
}