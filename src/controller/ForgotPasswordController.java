/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ForgotPasswordDao;
import dao.UserDao;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.ForgotPasswordModel;
import model.UserModel;
import view.ForgotPassword;
import view.Signup;

/**
 *
 * @author LENOVO
 */
public class ForgotPasswordController {
    
    private final ForgotPassword forgotpasswordView = null;
    
    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;
    private long lockoutEndTime = 0;
    private boolean forgotInProgress = false;
    private Component loginView;
    private final ForgotPasswordDao forgotpasswordDao = new ForgotPasswordDao();
    private String password;
    private Component fogotpassword;
    
    public ForgotPasswordController(forgotpasswordView) {
        this.forgotpasswordView = forgotpasswordView;
        
        
//        this.signupView = signupView;
//        this.adminDashboardView = adminDashboardView;
    }
    
    public void open() { 
        forgotpasswordView.setVisible(true); 
    }
    public void close() { 
        forgotpasswordView.dispose(); 
    }
    
    
    // ================= FORGOT PASSWORD BUTTON =================
    class ForgotPasswordButtonListener implements ActionListener {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            ForgotPassword forgotpasswordView = new ForgotPassword();
            close();
            ForgotPasswordController fpc = new ForgotPasswordController(forgotpasswordView);      // Attach controller
            fpc.open(); 
           
        }
    }
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

        ForgotPasswordModel forgotpasswordmodel = new ForgotPasswordModel(username, password);
 
        if (forgotpasswordDao.check(fogotpasswordmodel)) {
                    JOptionPane.showMessageDialog(fogotpassword, "User already exists!");
                    return;
                }

        
        
        
        ForgotPasswordDao.updatePassword(forgotpasswordmodel);
        if (forgotpasswordmodel == null) {
            JOptionPane.showMessageDialog(loginView, "Username not found!");
            return;
        }

        forgotpasswordmodel.setPassword(newPassword);
        forgotpasswordDao.updatePassword(forgotpasswordmodel);

        JOptionPane.showMessageDialog(loginView, "Password updated successfully!");
    
    }

}


