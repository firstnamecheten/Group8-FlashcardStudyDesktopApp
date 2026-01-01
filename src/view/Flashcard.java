/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD:src/dao/LoginDao.java
package dao;

import database.MySqlConnection;
import java.sql.Timestamp;
import java.sql.Statement;
import model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LoginModel;
=======
package view;

>>>>>>> 5f99df94f1dc494d6b254882e76c62a6bf8dbc49:src/view/Flashcard.java
/**
 *
 * @author bipin ranabhat
 */
<<<<<<< HEAD:src/dao/LoginDao.java
public class LoginDao {
    private final MySqlConnection mysql = new MySqlConnection();
    private String username;
    private String password;
    
    // INSERT LOGIN HISTORY
    public void insertLoginHistory(LoginModel loginmodel) {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO login_history (username, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
=======
public class Flashcard {
>>>>>>> 5f99df94f1dc494d6b254882e76c62a6bf8dbc49:src/view/Flashcard.java
    
    // LOGIN
    public LoginModel login(String username, String password) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM signup_history WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new LoginModel(
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }

}