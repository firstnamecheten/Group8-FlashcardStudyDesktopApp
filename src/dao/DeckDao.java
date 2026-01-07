package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DeckModel;

public class DeckDao {
    private MySqlConnection mysql;

    public DeckDao() {
        mysql = new MySqlConnection();
    }

    // Insert new deck
    public void insertDeck(DeckModel deck) throws SQLException {
        mysql.openConnection();
        Connection conn = mysql.db.getConnection();

        String sql = "INSERT INTO decks (front_side, back_side) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, deck.getFrontSide());
        stmt.setString(2, deck.getBackSide());
        stmt.executeUpdate();

        stmt.close();
        mysql.closeConnection();
    }

    // Retrieve all decks
    public ResultSet getAllDecks() throws SQLException {
        mysql.openConnection();
        Connection conn = mysql.db.getConnection();

        String sql = "SELECT * FROM decks";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }
}