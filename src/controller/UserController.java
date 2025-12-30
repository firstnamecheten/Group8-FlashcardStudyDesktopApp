package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserModel;
import view.Dashboard;
import view.Signup;
import view.Login;

public class UserController {

    private final UserDao userDao = new UserDao();

    private final Signup userView;
    
    //Constructor
    public UserController(Signup userView) {
        this.userView = userView;
 
        
        // Register listeners for buttons in Signup view
        userView.AddUserListener(new SignUpListener());   // handles signup button
        userView.LoginButtonListener(new LoginListener()); // handles "Go to Login" button

    }

    

    public void open() {
        userView.setVisible(true);
    }

    public void close() {
        userView.dispose();
    }

    // 🔹 When user clicks "Login" button on Signup page
    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginView = new Login();
            close();
            LoginController controller = new LoginController(loginView);      // Attach controller
            controller.open();  
            
        }
    }

    // 🔹 When user clicks "Signup" button
    private class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String username = userView.getUsernameField().getText().trim();
                String password = userView.getPasswordField().getText().trim();
                String confirmPassword = userView.getConfirmPasswordField().getText().trim();

                // Treat placeholders as empty
                if (username.startsWith(" Enter")) username = " ";
                if (password.startsWith("Enter")) password = " ";
                if (confirmPassword.startsWith("Re-type")) confirmPassword = " ";

                // Required fields check
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(userView, "All fields are required!");
                    return;
                }

                // Password match check
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(userView, "Passwords do not match!");
                    return;
                }

                // Create model
                UserModel usermodel = new UserModel(username, password, confirmPassword);    // look from here 
                // Check if user already exists
                if (userDao.check(usermodel)) {
                    JOptionPane.showMessageDialog(userView, "User already exists!");
                    return;
                }

                // Save user
                userDao.signup(usermodel);
                JOptionPane.showMessageDialog(userView, "Signup successful!");        // ✓ Success message
                Login loginView = new Login();
                close();
                LoginController controller = new LoginController(loginView);      // Attach controller
                controller.open();  
                

            } catch (Exception ex) {
                ex.printStackTrace(); // optional for debugging
                JOptionPane.showMessageDialog(userView, "Signup failed!");
            }
        }
    }
}
         

      

