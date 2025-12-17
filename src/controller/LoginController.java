package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserModel;
import view.Dashboard;
import view.Login;
import view.Signup;

public class LoginController {

    private final Login loginView;
    private final UserDao userDao = new UserDao();
    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;
    private long lockoutEndTime = 0; // timestamp when lockout ends

    public LoginController(Login loginView) {
        this.loginView = loginView;

        // Attach listeners from Login view
        loginView.LoginButtonListener(new LoginButtonListener());
        loginView.ForgotPasswordButtonListener(new ForgotPasswordButtonListener());
        loginView.CreateAccountButtonListener(new CreateAccountButtonListener());
    }

    public void open() {
        this.loginView.setVisible(true);
    }

    public void close() {
        this.loginView.dispose();
    }

    private void setLoginEnabled(boolean enabled) {
        loginView.getUsernameField().setEnabled(enabled);
        loginView.getPasswordField().setEnabled(enabled);
        loginView.getLoginButton().setEnabled(enabled);
    }

    // Login attempt logic with attempt counting and lockout
    private UserModel tryLogin(String username, String password) {

        long now = System.currentTimeMillis();
        if (now < lockoutEndTime) {
            long remainingSeconds = (lockoutEndTime - now) / 1000;
            JOptionPane.showMessageDialog(
                    loginView,
                    "Too many attempts! Try again in " + remainingSeconds + " seconds."
            );
            return null;
        }

        UserModel user = userDao.login(username, password);

        if (user != null) {
            // success
            loginAttempts = 0;
            return user;
        } else {
            // wrong login
            loginAttempts++;

            if (loginAttempts >= MAX_ATTEMPTS) {
                // lock for 5 minutes
                lockoutEndTime = System.currentTimeMillis() + (5 * 60 * 1000);

                setLoginEnabled(false);

                JOptionPane.showMessageDialog(
                        loginView,
                        "Too many wrong attempts! Login disabled for 5 minutes."
                );

                // Re-enable after 5 minutes
                new javax.swing.Timer(5 * 60 * 1000, event -> {
                    setLoginEnabled(true);
                    loginAttempts = 0;
                    lockoutEndTime = 0;
                    JOptionPane.showMessageDialog(loginView, "You can try logging in again now.");
                }).start();

            } else {
                JOptionPane.showMessageDialog(
                        loginView,
                        "Wrong username or password! Attempt " + loginAttempts + " of " + MAX_ATTEMPTS
                );
            }

            return null;
        }
    }

    // LOGIN BUTTON LISTENER
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Inside LoginButtonListener
            String username = loginView.getUsernameField().getText().trim();
            String password = loginView.getPasswordField().getText().trim();

            // Treat placeholders as empty
            if (username.equals("Enter the username") || username.equals(" Enter the username")) username = "";
            if (password.equals("Enter the password") || password.equals(" Enter the password")) password = "";

            // Required fields check
            if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "All fields are required!");
            return;
}

            // Use tryLogin to handle attempts + lockout
            UserModel user = tryLogin(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(loginView, "Login successful!");

                // store login history (user_id, username, password)
                userDao.insertLoginHistory(user.getUserId(), user.getUsername(), password);

                Dashboard d = new Dashboard();
                d.setVisible(true);
                loginView.dispose();   
            }
        }
    }

    // FORGOT PASSWORD BUTTON LISTENER
    class ForgotPasswordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleForgotPassword();
        }
    }

    // CREATE ACCOUNT BUTTON LISTENER
    class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Signup signup = new Signup();
            UserController uc = new UserController(signup);
            uc.open();
            loginView.dispose();
        }
    }

    // Forgot password logic
    public void handleForgotPassword() {

    // Ask for username once
    String username = JOptionPane.showInputDialog(null, "Enter your username:");

    // If user pressed cancel
    if (username == null) {
        return;
    }

    username = username.trim();

    // Treat placeholder or empty as invalid
    if (username.isEmpty() || username.equals("Enter the username") || username.equals(" Enter the username")) {
        JOptionPane.showMessageDialog(null, "Username is required!");
        return;
    }

    // Check if user exists
    UserModel user = userDao.getUserByUsername(username);

    if (user == null) {
        JOptionPane.showMessageDialog(null, "Username not found!");
        return;
    }

    // Ask for new password
    String newPassword = JOptionPane.showInputDialog(null, "Enter your new password:");

    // If user pressed cancel
    if (newPassword == null) {
        return;
    }

    newPassword = newPassword.trim();

    // Validate new password
    if (newPassword.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Password cannot be empty!");
        return;
    }

    // Update password
    user.setPassword(newPassword);
    userDao.updatePassword(user);

    JOptionPane.showMessageDialog(null, "Password updated successfully!");
}
}