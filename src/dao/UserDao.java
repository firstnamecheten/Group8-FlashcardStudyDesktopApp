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

public class UserDao {

    private final MySqlConnection mysql = new MySqlConnection();

    // SIGNUP
    public void signup(UserModel user) {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO signup_history (username, password, confirmpassword) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getconfirmpassword());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }

    // CHECK DUPLICATE USER
    public boolean checkUser(UserModel user) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM signup_history WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            ResultSet result = pstmt.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

    // LOGIN
    public UserModel login(String username, String password) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM signup_history WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new UserModel(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }

    // INSERT LOGIN HISTORY
    // INSERT LOGIN HISTORY and return login_id
    public int insertLoginHistory(int userId, String username, String password) {
    Connection conn = mysql.openConnection();
    String sql = "INSERT INTO login_history (user_id, username, password) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setInt(1, userId);
        pstmt.setString(2, username);
        pstmt.setString(3, password);
        pstmt.executeUpdate();

        // ✅ Get the generated login_id
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1); // return login_id
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        mysql.closeConnection(conn);
    }
    return -1; // return -1 if something failed
}

    // GET USER BY USERNAME
    public UserModel getUserByUsername(String username) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM signup_history WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new UserModel(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }

    // UPDATE PASSWORD
    public void updatePassword(UserModel user) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE signup_history SET password = ? WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }
    

   // INSERT LOGOUT HISTORY
    public void insertLogoutHistory(int loginId, int userId, String sessionId, Timestamp logoutTime) {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO logout_history (login_id, user_id, session_id, logout_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, loginId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, sessionId);

            if (logoutTime != null) {
                pstmt.setTimestamp(4, logoutTime);
            } else {
                pstmt.setNull(4, java.sql.Types.TIMESTAMP);
            }

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }

    // FETCH LOGOUT HISTORY (for AdminDashboard)
    public ResultSet getLogoutHistory() {
        Connection conn = mysql.openConnection();
        String sql = "SELECT logout_id, login_id, user_id, session_id, logout_time FROM logout_history";
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}



// this was made to check username and email. Check garcha jaba samma rows sakidaina taba samma. Yedi same cha bhane tya value store garirako huncha. Yo method call garcha. Jaba yo method call huncha kunchai method call huneh bhayo?--> check method!