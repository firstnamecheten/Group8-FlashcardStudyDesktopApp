/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import view.Dashboard;
import view.Account;
import view.StudyHistory;
import javax.swing.JOptionPane;
/**
 *
 * @author bipin ranabhat
 */
public class AccountController {
    private final Dashboard dashboard;

    public AccountController(Dashboard dashboard) {
        this.dashboard = dashboard;
        attachEventListeners();
        setupProfileButton();
    }

    private void setupProfileButton() {
        // This makes the profile icon (jButton2) show the popup menu when clicked
        dashboard.getProfileButton().addActionListener(e -> {
            dashboard.getProfilePopupMenu().show(
                dashboard.getProfileButton(),
                0,
                dashboard.getProfileButton().getHeight()
            );
        });
    }
    
    private void attachEventListeners() {
        // Use ONLY the public getters — NEVER direct field names
        dashboard.getAccountMenuItem().addActionListener(e -> openAccountPage());
        
        dashboard.getDarkLightModeMenuItem().addActionListener(e -> toggleDarkLightMode());
        
        dashboard.getStudyHistoryMenuItem().addActionListener(e -> openStudyHistory());
        
        dashboard.getLogoutMenuItem().addActionListener(e -> logout());
    }

    private void openAccountPage() {
        // Open your Account JFrame
        Account accountFrame = new Account();
        accountFrame.setVisible(true);
        accountFrame.setLocationRelativeTo(dashboard);
        // Optional: hide dashboard if you want single window flow
        // dashboard.setVisible(false);
    }

    private void toggleDarkLightMode() {
       Color currentBg = dashboard.getContentPane().getBackground();

        if (currentBg.getRed() > 200) { // Light mode → switch to Dark
            dashboard.getContentPane().setBackground(new Color(30, 30, 30));
            dashboard.getTopPanel().setBackground(new Color(20, 20, 20));
            dashboard.getMainPanel().setBackground(new Color(45, 45, 45));
            dashboard.getMainPanel().setOpaque(true);

            dashboard.getCreateButton().setBackground(new Color(70, 70, 70));
            dashboard.getCreateButton().setForeground(Color.WHITE);

            dashboard.getHomeButton().setBackground(new Color(50, 50, 50));
            dashboard.getHomeButton().setForeground(Color.WHITE);
            dashboard.getLibraryButton().setBackground(new Color(50, 50, 50));
            dashboard.getLibraryButton().setForeground(Color.WHITE);

            setAllTextWhite(dashboard.getContentPane());
            dashboard.getDarkLightModeMenuItem().setText("Light Mode");

        } else { // Dark mode → switch to Light
            dashboard.getContentPane().setBackground(Color.WHITE);
            dashboard.getTopPanel().setBackground(Color.WHITE);
            dashboard.getMainPanel().setBackground(Color.WHITE);
            dashboard.getMainPanel().setOpaque(true);

            dashboard.getCreateButton().setBackground(new Color(204, 204, 204));
            dashboard.getCreateButton().setForeground(Color.BLACK);

            dashboard.getHomeButton().setBackground(new Color(240, 240, 240));
            dashboard.getHomeButton().setForeground(Color.BLACK);
            dashboard.getLibraryButton().setBackground(new Color(240, 240, 240));
            dashboard.getLibraryButton().setForeground(Color.BLACK);

            setAllTextBlack(dashboard.getContentPane());
            dashboard.getDarkLightModeMenuItem().setText("Dark/Light Mode");
        }
    }

    private void setAllTextWhite(Component component) {
        component.setForeground(Color.WHITE);
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                setAllTextWhite(child);
            }
        }
    }

    private void setAllTextBlack(Component component) {
        component.setForeground(Color.BLACK);
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                setAllTextBlack(child);
            }
        }
    }
    private void setForegroundRecursively(Component component, Color color) {
    component.setForeground(color);
    if (component instanceof Container container) {
        for (Component child : container.getComponents()) {
            setForegroundRecursively(child, color);
        }
    }
}

    private void openStudyHistory() {
        StudyHistory historyFrame = new StudyHistory();
        historyFrame.setVisible(true);
        historyFrame.setLocationRelativeTo(dashboard);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            dashboard,
            "Are you sure you want to log out?",
            "Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            dashboard.dispose();
            // Replace with your actual login screen
            // new view.Login().setVisible(true);
        }
    }
}
