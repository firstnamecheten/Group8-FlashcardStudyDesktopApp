/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.MySqlConnection;
import model.UserModel;
import java.sql.*;

public class UserDao {

    public static UserModel login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private MySqlConnection mysql = new MySqlConnection();
    private String email_Text_Field;

    // Signup user
    public void signUp(UserModel user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error inserting user: " + ex);
        }
    }

    // Check duplicate username/email
    public boolean exists(UserModel user) {
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            System.out.println("Error checking user: " + ex);
            return false;
        }
    }

    // Login with username + password
    public UserModel getUserByUsername(String username) {
    Connection conn = mysql.openConnection();
    UserModel user = null;
    String sql = "SELECT * FROM users WHERE username = ?";
    
    try(PreparedStatement pstm = conn.prepareStatement(sql)){
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            user = new UserModel(rs.getString("username"), rs.getString("password"), email_Text_Field);
        }
    } catch(Exception e) {
        System.out.println(e);
    } finally {
        mysql.closeConnection(conn);
    }
    return user;
}

public void updatePassword(UserModel user){
    Connection conn = mysql.openConnection();
    String sql = "UPDATE users SET password=? WHERE username=?";
    
    try(PreparedStatement pstm = conn.prepareStatement(sql)){
        pstm.setString(1, user.getPassword());
        pstm.setString(2, user.getUsername());
        pstm.executeUpdate();
    } catch(Exception e){
        System.out.println(e);
    } finally {
        mysql.closeConnection(conn);
    }
}

    public boolean check(UserModel usermodel) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    

// this was made to check username and email. Check garcha jaba samma rows sakidaina taba samma. Yedi same cha bhane tya value store garirako huncha. Yo method call garcha. Jaba yo method call huncha kunchai method call huneh bhayo?--> check method!