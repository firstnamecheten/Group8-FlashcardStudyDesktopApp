package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserModel;
import view.Signup;
import view.Login;

public class UserController {

    private final UserDao userDao = new UserDao();
    private final Signup userView;

    public UserController(Signup userView) {
        this.userView = userView;

        // Register listeners
        userView.AddUserListener(new SignUpListener());
        userView.LoginButtonListener(new LoginListener());
    }

    public void open() {
        this.userView.setVisible(true);
    }

    public void close() {
        this.userView.dispose();
    }

    // When user clicks "Login" button on Signup page
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginView = new Login();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            userView.dispose();
        }
    }

    // When user clicks "Signup" button
    class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Inside SignUpListener
                String username = userView.getUsernameField().getText().trim();
                String email = userView.getEmailField().getText().trim();
                String password = userView.getPasswordField().getText().trim();
                String confirmPassword = userView.getConfirmPasswordField().getText().trim();

                // Treat placeholders as empty
                if (username.equals("Enter the username") || username.equals(" Enter the username")) username = "";
                if (email.equals("Enter the email") || email.equals(" Enter the email")) email = "";
                if (password.equals("Enter the password") || password.equals(" Enter the password")) password = "";
                if (confirmPassword.equals("Re-type to confirm password") || confirmPassword.equals(" Re-type to confirm password")) confirmPassword = "";

// Required fields check
if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
    JOptionPane.showMessageDialog(userView, "All fields are required!");
    return;
}

// Password match check
if (!password.equals(confirmPassword)) {
    JOptionPane.showMessageDialog(userView, "Passwords do not match!");
    return;
}

                // Create model
                UserModel usermodel = new UserModel(username, email, password, confirmPassword);

                // Check if user already exists
                if (userDao.check(usermodel)) {
                    JOptionPane.showMessageDialog(userView, "User already exists!");
                    return;
                }

                // Save user
                userDao.signUp(usermodel);

                // Success message
                JOptionPane.showMessageDialog(userView, "Signup successful!");

                // Redirect to login page
                Login loginView = new Login();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                userView.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(userView, "Something went wrong!");
            }
        }
    }

    // Helper (not strictly needed but kept if you want it)
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}

         

