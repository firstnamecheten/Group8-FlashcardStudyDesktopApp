package dao;

import database.MySqlConnection;
import model.UserModel;
import java.sql.*;

public class UserDao {

    private final MySqlConnection mysql = new MySqlConnection();

    // ✅ SIGNUP
    public void signUp(UserModel user) {
        String sql = "INSERT INTO signup_history (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Signup error: " + e);
        }
    }

    // ✅ CHECK DUPLICATE USER
    public boolean check(UserModel user) {
        String sql = "SELECT * FROM signup_history WHERE username=? OR email=?";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            return ps.executeQuery().next();

        } catch (Exception e) {
            System.out.println("Check error: " + e);
            return false;
        }
    }

    // ✅ LOGIN (used by LoginController.tryLogin)
    public UserModel login(String username, String password) {
        String sql = "SELECT * FROM signup_history WHERE username=? AND password=?";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserModel(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e);
        }

        return null;
    }

    // ✅ INSERT LOGIN HISTORY (called in LoginButtonListener)
    public void insertLoginHistory(int userId, String username) {
        String sql = "INSERT INTO login_history (user_id, username) VALUES (?, ?)";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Login history error: " + e);
        }
    }

    // ✅ GET USER BY USERNAME (used in Forgot Password)
    public UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM signup_history WHERE username=?";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserModel(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("Get user error: " + e);
        }

        return null;
    }

    // ✅ UPDATE PASSWORD (used in Forgot Password)
    public void updatePassword(UserModel user) {
        String sql = "UPDATE signup_history SET password=? WHERE username=?";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUsername());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Password update error: " + e);
        }
    }
}

// this was made to check username and email. Check garcha jaba samma rows sakidaina taba samma. Yedi same cha bhane tya value store garirako huncha. Yo method call garcha. Jaba yo method call huncha kunchai method call huneh bhayo?--> check method!