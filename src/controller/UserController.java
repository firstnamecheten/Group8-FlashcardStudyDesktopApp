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
    private final Signup signupView;
    private final Login loginView;
    
    //Constructor
    public UserController(Signup signupView, Login loginView) {
        this.signupView = signupView;
        this.loginView = loginView;
        
        // Register listeners for buttons in Signup view
        signupView.AddUserListener(new SignUpListener());   // handles signup button
        signupView.LoginButtonListener(new LoginListener()); // handles "Go to Login" button

    }

    public void open() {
        signupView.setVisible(true);
    }

    public void close() {
        signupView.dispose();
    }

    // 🔹 When user clicks "Login" button on Signup page
    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login log = new Login();
            close();
            LoginController controller = new LoginController(log);      // Attach controller
            controller.open();  
            
            
            
        }
    }

    // 🔹 When user clicks "Signup" button
    private class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String username = signupView.getUsernameField().getText().trim();
                String password = signupView.getPasswordField().getText().trim();
                String confirmPassword = signupView.getConfirmPasswordField().getText().trim();

                // Treat placeholders as empty
                if (username.startsWith(" Enter")) username = " ";
                if (password.startsWith("Enter")) password = " ";
                if (confirmPassword.startsWith("Re-type")) confirmPassword = " ";

                // Required fields check
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(signupView, "All fields are required!");
                    return;
                }

                // Password match check
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(signupView, "Passwords do not match!");
                    return;
                }

                // Create model
                UserModel usermodel = new UserModel(username, password, confirmPassword);    // look from here 
                // Check if user already exists
                if (userDao.checkUser(usermodel)) {
                    JOptionPane.showMessageDialog(signupView, "User already exists!");
                    return;
                }

                // Save user
                userDao.signup(usermodel);
                JOptionPane.showMessageDialog(signupView, "Signup successful!");        // ✓ Success message

                // 🔹 After signup, immediately switch to Login page
                loginView.setVisible(true);          // show original Login
                signupView.setVisible(false);        // hide original Signup
                
            } catch (Exception ex) {
                ex.printStackTrace(); // optional for debugging
                JOptionPane.showMessageDialog(signupView, "Signup failed!");
            }
        }
    }
}
         

