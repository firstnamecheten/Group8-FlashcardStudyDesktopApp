package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.UserModel;
import view.Dashtwo;
import view.Login;
import view.Signup;

public class LoginController {

    private final Login loginView;
    private final UserDao userDao = new UserDao();

    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;
    private long lockoutEndTime = 0;
    private boolean forgotInProgress = false;

    public LoginController(Login loginView) {
        this.loginView = loginView;

        loginView.LoginButtonListener(new LoginButtonListener());
        loginView.ForgotPasswordButtonListener(new ForgotPasswordButtonListener());
        loginView.CreateAccountButtonListener(new CreateAccountButtonListener());
    }

    public void open() { loginView.setVisible(true); }
    public void close() { loginView.dispose(); }

    private void setLoginEnabled(boolean enabled) {
        loginView.getUsernameField().setEnabled(enabled);
        loginView.getPasswordField().setEnabled(enabled);
        loginView.getLoginButton().setEnabled(enabled);
    }

    // ================= LOGIN LOGIC =================
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
            loginAttempts = 0;
            return user; // ✅ return user, don't show popup here
        }

        loginAttempts++;
        if (loginAttempts >= MAX_ATTEMPTS) {
            lockoutEndTime = System.currentTimeMillis() + (5 * 60 * 1000);
            setLoginEnabled(false);

            JOptionPane.showMessageDialog(
                    loginView,
                    "Too many wrong attempts! Login disabled for 5 minutes."
            );

            new Timer(5 * 60 * 1000, e -> {
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

    // ================= LOGIN BUTTON =================
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsernameField().getText().trim();
            String password = loginView.getPasswordField().getText().trim();

            if (username.equalsIgnoreCase("Enter the username")) username = "";
            if (password.equalsIgnoreCase("Enter the password")) password = "";

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "All fields are required!");
                return;
            }

            UserModel user = tryLogin(username, password);

            if (user != null) {
                // ✅ Success popup appears only here
                JOptionPane.showMessageDialog(loginView, "Login successful!");
                userDao.insertLoginHistory(user.getUserId(), user.getUsername(), password);

                Dashtwo d = new Dashtwo(user); // ✅ pass user context
                d.setVisible(true);
                loginView.dispose();
            }
        }
    }

    // ================= FORGOT PASSWORD BUTTON =================
    class ForgotPasswordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (forgotInProgress) return;

            forgotInProgress = true;
            try {
                handleForgotPassword();
            } finally {
                forgotInProgress = false;
            }
        }
    }

    // ================= CREATE ACCOUNT =================
    class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Signup signup = new Signup();
            UserController uc = new UserController(signup);
            uc.open();
            loginView.dispose();
        }
    }

    // ================= FORGOT PASSWORD LOGIC =================
    private void handleForgotPassword() {
        JTextField usernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "New Password:", newPasswordField
        };

        int option = JOptionPane.showConfirmDialog(
                loginView,
                message,
                "Forgot Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        String username = usernameField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();

        if (username.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "All fields are required!");
            return;
        }

        UserModel user = userDao.getUserByUsername(username);

        if (user == null) {
            JOptionPane.showMessageDialog(loginView, "Username not found!");
            return;
        }

        user.setPassword(newPassword);
        userDao.updatePassword(user);

        JOptionPane.showMessageDialog(loginView, "Password updated successfully!");
    }
}