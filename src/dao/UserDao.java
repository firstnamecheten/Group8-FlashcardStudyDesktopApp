package dao;

import database.MySqlConnection;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDao {

    private final MySqlConnection mysql = new MySqlConnection();

    // SIGNUP: now RETURNS the user with generated user_id
    public UserModel signUp(UserModel user) {
        String sql = "INSERT INTO signup_history (username, email, password, confirmpassword) VALUES (?, ?, ?, ?)";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getconfirmpassword());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));   // user_id from MySQL
                }
            }

            return user;

        } catch (Exception e) {
            System.out.println("Signup error: " + e);
            return null;
        }
    }

    // CHECK DUPLICATE USER
    public boolean check(UserModel user) {
        String sql = "SELECT * FROM signup_history WHERE username = ? OR email = ?";

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

    // LOGIN (used by LoginController.tryLogin)
    public UserModel login(String username, String password) {
        String sql = "SELECT * FROM signup_history WHERE username = ? AND password = ?";

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

    // INSERT LOGIN HISTORY
    public void insertLoginHistory(int userId, String username, String password) {
        String sql = "INSERT INTO login_history (user_id, username, password) VALUES (?, ?, ?)";

        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Login history error: " + e);
        }
    }

    // GET USER BY USERNAME (used in Forgot Password)
    public UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM signup_history WHERE username = ?";

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

    // UPDATE PASSWORD (used in Forgot Password)
    public void updatePassword(UserModel user) {
        String sql = "UPDATE signup_history SET password = ? WHERE username = ?";

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