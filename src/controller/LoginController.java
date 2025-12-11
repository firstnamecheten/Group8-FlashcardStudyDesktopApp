/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.UserModel;
import view.Dashboard;
import view.Login;
import view.Signup;

public class LoginController {

    private Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
    }
    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;

public boolean login(String username, String password) {
    if(loginAttempts >= MAX_ATTEMPTS){
        JOptionPane.showMessageDialog(null, "Too many wrong tries! Please wait 5 seconds.");
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        loginAttempts = 0;
        return false;
    }

    UserDao dao = new UserDao();
    UserModel user = dao.login(username, password);

    if(user != null){
        loginAttempts = 0;
        return true;
    } else {
        loginAttempts++;
        JOptionPane.showMessageDialog(null, "Wrong username or password! Attempt " 
                                      + loginAttempts + " of " + MAX_ATTEMPTS);
        return false;
    }
}
    
    // OPEN LOGIN VIEW
    public void open() {
        loginView.setVisible(true);
    }

    // FORGOT PASSWORD HANDLER
    public void handleForgotPassword() {
    String username = JOptionPane.showInputDialog(null, "Enter your username:");

    if(username == null || username.isEmpty()) return; // user cancelled

    UserDao dao = new UserDao(); // create instance
    UserModel user = dao.getUserByUsername(username); // use instance

    if(user != null){
        String newPassword = JOptionPane.showInputDialog(null, "Enter your new password:");
        if(newPassword != null && !newPassword.isEmpty()){
            user.setPassword(newPassword);
            dao.updatePassword(user);  // update password in the database
            JOptionPane.showMessageDialog(null, "Password updated successfully!");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Username not found!");
    }
}

    public void attachLoginListener() {
    loginView.addLoginButtonListener(e -> {
        JTextField username = loginView.getUsernameField();
        JTextField password = loginView.getPasswordField();

        if (login(username, password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");

            Dashboard dash = new Dashboard();
            dash.setVisible(true);
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password!");
        }
    });
}

    // OPEN SIGNUP PAGE FROM LOGIN
    public void openSignUpPage() {
        Signup signup = new Signup();
        signup.setVisible(true);
    }

    private boolean login(JTextField username, JTextField password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

