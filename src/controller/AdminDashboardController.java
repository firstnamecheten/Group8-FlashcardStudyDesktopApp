/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.AdminDashboard;
import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserModel;

/**
 *
 * @author LENOVO
 */
public class AdminDashboardController {

    private final AdminDashboard adminDashboardView = null;
    
    
    
    public AdminDashboardController(adminDashboardView){
        this.adminDashboardView = adminDashboardView;
        
        adminDashboardView.LoadSignupButtonListener(new LoadSignUpListener());
        
    }

    private static class LoadSignUpListener implements ActionListener {
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
            // Admin Dahboard action perfomed
            if (username.equals("Admin") && password.equals("1234")) {
            JOptionPane.showMessageDialog(adminDashboardView, "Admin Login successful!");
            AdminDashboard asminDashboardView = new AdminDashboard();
            close(); // close current login/signup window
            LoginController adcontroller = new LoginController(loginView); 
            adcontroller.open(); // open the Admin Dashboard

        }  
        }
    }
}
