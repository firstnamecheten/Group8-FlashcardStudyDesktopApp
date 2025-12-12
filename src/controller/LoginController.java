/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    private final UserDao userdao = new UserDao();   // ✅ have ONE DAO here
    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;

    // ✅ CORRECT CONSTRUCTOR
    public LoginController(Login loginView) {
        this.loginView = loginView;

        // Attach listeners from Login view
        loginView.LoginButtonListener(new LoginButtonListener());
        loginView.ForgotPasswordButtonListener(new ForgotPasswordButtonListener());
        loginView.CreateAccountButtonListener(new CreateAccountButtonListener());
    }

    // ✅ Show login window
    public void open() {
        this.loginView.setVisible(true);
    }

    // ✅ Close login window
    public void close() {
        this.loginView.dispose();
    }

    // ✅ MAIN LOGIN LOGIC (uses DAO and returns UserModel)
    private UserModel tryLogin(String username, String password) {

        if (loginAttempts >= MAX_ATTEMPTS) {
            JOptionPane.showMessageDialog(null, "Too many wrong tries! Please wait 5 seconds.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // ignore
            }
            loginAttempts = 0;
            return null;
        }

        UserModel user = userdao.login(username, password);

        if (user != null) {
            loginAttempts = 0;
            return user;
        } else {
            loginAttempts++;
            JOptionPane.showMessageDialog(
                    null,
                    "Wrong username or password! Attempt " + loginAttempts + " of " + MAX_ATTEMPTS
            );
            return null;
        }
    }

    // ✅ LISTENER: LOGIN BUTTON
    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = loginView.getUsernameField().getText();
            String password = loginView.getPasswordField().getText();

            // use the login logic
            UserModel user = tryLogin(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(null, "Login successful!");

                // Insert login history
                userdao.insertLoginHistory(user.getUserId(), user.getUsername());

                // Open dashboard
                Dashboard dash = new Dashboard();
                dash.setVisible(true);
                loginView.dispose();
            }
        }
    }

    // ✅ LISTENER: FORGOT PASSWORD
    class ForgotPasswordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleForgotPassword();
        }
    }

    // ✅ LISTENER: SIGNUP BUTTON
    class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Signup signup = new Signup();
            UserController uc = new UserController(signup);
            uc.open();
            loginView.dispose();
        }
    }

    // ✅ Forgot password logic
    public void handleForgotPassword() {
        String username = JOptionPane.showInputDialog(null, "Enter your username:");

        if (username == null || username.isEmpty()) return;

        UserModel user = userdao.getUserByUsername(username);

        if (user != null) {
            String newPassword = JOptionPane.showInputDialog(null, "Enter your new password:");
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword);
                userdao.updatePassword(user);
                JOptionPane.showMessageDialog(null, "Password updated successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Username not found!");
        }
    }
}
