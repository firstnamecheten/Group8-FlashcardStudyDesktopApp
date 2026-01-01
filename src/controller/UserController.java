/*package controller;

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
<<<<<<< HEAD
    
    //Constructor
    public UserController(Signup userView) {         // Local variable (parameter i.e Signup userview) is being used to bring data (userView) into the constructor/method. This local variable parameter "userView" is brought in from outside the class when you create a new  object.
        this.userView = userView;                    // userView is a local variable that is declared and assigned here inside a constructor. And userView is a local variable storing value userView. (Note: Local variable (right hand side "userView") is assigned to an instance variable (left hand side "this.userView") so the object can keep that data alive after the constructor ends)
 
        
        // Register listeners for buttons in Signup view
        userView.AddUserListener(new SignUpListener());   // handles signup button
        userView.LoginButtonListener(new LoginListener()); // handles "Go to Login" button    (LoginButtonListener attaches a listener to the login button. And new LoginListener creates a new listener object for login button actions. 
=======

    public UserController(Signup userView) {
        this.userView = userView;
>>>>>>> 5f99df94f1dc494d6b254882e76c62a6bf8dbc49

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
    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginView = new Login();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            userView.dispose();
        }
    }

    // When user clicks "Signup" button
    private class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String username = userView.getUsernameField().getText().trim();
                String password = userView.getPasswordField().getText().trim();
                String confirmPassword = userView.getConfirmPasswordField().getText().trim();

                // Treat placeholders as empty
                if (username.startsWith("Enter")) username = "";
                if (password.startsWith("Enter")) password = "";
                if (confirmPassword.startsWith("Re-type")) confirmPassword = "";

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
                UserModel usermodel = new UserModel(username, password, confirmPassword);

                // Check if user already exists
                if (userDao.check(usermodel)) {
                    JOptionPane.showMessageDialog(userView, "User already exists!");
                    return;
                }

                // Save user
                UserModel newUser = userDao.signUp(usermodel);
                if (newUser == null) {
                    JOptionPane.showMessageDialog(userView, "Signup failed!");
                    return;
                }

                // ✅ Success message
                JOptionPane.showMessageDialog(userView, "Signup successful!");

                // ✅ Close signup and return to Login
                Login loginView = new Login();
<<<<<<< HEAD
                close();
                LoginController controller = new LoginController(loginView);      // Attach controller
                controller.open();               
=======
                LoginController loginController = new LoginController(loginView);
                loginController.open();

                // hide signup
                userView.dispose(); // closes the signup frame
                
>>>>>>> 5f99df94f1dc494d6b254882e76c62a6bf8dbc49

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(userView, "Something went wrong!");
            }
        }
    }
}
*/
         

