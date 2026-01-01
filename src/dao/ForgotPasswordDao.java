/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ForgotPasswordModel;

/**
 *
 * @author LENOVO
 */
public class ForgotPasswordDao {
    
    private final MySqlConnection mysql = new MySqlConnection();
    
    // UPDATE PASSWORD
    public void updatePassword(ForgotPasswordModel forgotpasswordmodel) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE signup_history SET password = ? WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, forgotpasswordmodel.getPassword());
            pstmt.setString(2, forgotpasswordmodel.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
