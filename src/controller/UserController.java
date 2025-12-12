package controller;

import controller.LoginController.LoginButtonListener;
import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.nio.file.Files.exists;
import model.UserModel;
import view.Signup;
import javax.swing.*;
import model.UserModel;
import view.Signup;
import view.Login;

import static java.nio.file.Files.exists;
public class UserController {
    private final UserDao userdao = new UserDao();
    private final Signup userView;

    public UserController(Signup userView){
        this.userView = userView;
        
        userView.AddUserListener(new SignUpListener());
        userView.LoginButtonListener(new LoginListener());
    }
    
    public void open(){
        this.userView.setVisible(true);
    }
    public void close(){
        this.userView.dispose();
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login log = new Login();
            LoginController lc = new LoginController(log);
            lc.open();
            
        }
    }
    
    class SignUpListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String username = userView.getUsernameField().getText().trim();
            String email = userView.getEmailField().getText().trim();
            String password = userView.getPasswordField().getText().trim();
            String confirmPassword = userView.getConfirmPasswordField().getText().trim();

            // ✅ Required field check (works for 1 empty OR all empty)
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(userView, "All fields are required!");
                return;
            }

            // ✅ Password match check
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(userView, "Passwords do not match!");
                return;
            }

            // ✅ Create model
            UserModel usermodel = new UserModel(username, email, password);

            // ✅ Check if user exists
            boolean exists = userdao.check(usermodel);
            if (exists) {
                JOptionPane.showMessageDialog(userView, "User already exists!");
                return;
            }

            // ✅ Save user
            userdao.signUp(usermodel);

            // ✅ Success popup
            JOptionPane.showMessageDialog(userView, "Signup successful!");

            // ✅ Open login page
            Login loginView = new Login();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            userView.dispose();

        } catch (Exception ext) {
            System.out.println(ext);
        }
     }
   }
}