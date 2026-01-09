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
import view.Dashtwo;
import view.Signup;
import view.Login;
import view.UserBasedFlashcardOwnership;

public class UserBasedFlashcardOwnershipController {

    private final UserDao userDao = new UserDao();
    private final UserBasedFlashcardOwnership ufView;
    private final Dashtwo dash;
    private UserModel user;

    
    //Constructor
    public UserBasedFlashcardOwnershipController(UserBasedFlashcardOwnership ufView, Dashtwo dash) {
        this.ufView = ufView;
        this.dash = dash;
        
        ufView.HomeButtonListener(new HomeListener()); // handles "Go to Login" button
    }

    public void open() {
        ufView.setVisible(true);
        if (user != null) {
        ufView.showUserInfo(user.getUserId(), user.getUsername());
        }
    }

    public void close() {
        ufView.dispose();
    }
    public void setUser(UserModel user) {
    this.user = user;
    }


    class HomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        close();
        dash.setVisible(true); // reopen original signup page
        }
    }
}
    

         

