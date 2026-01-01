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
    public UserController(Signup userView) {         // Local variable (parameter i.e Signup userview) is being used to bring data (userView) into the constructor/method. This local variable parameter "userView" is brought in from outside the class when you create a new  object.
        this.userView = userView;                    // userView is a local variable that is declared and assigned here inside a constructor. And userView is a local variable storing value userView. (Note: Local variable (right hand side "userView") is assigned to an instance variable (left hand side "this.userView") so the object can keep that data alive after the constructor ends)
 
        
        // Register listeners for buttons in Signup view
        userView.AddUserListener(new SignUpListener());   // handles signup button
        userView.LoginButtonListener(new LoginListener()); // handles "Go to Login" button    (LoginButtonListener attaches a listener to the login button. And new LoginListener creates a new listener object for login button actions. 

    }

    

    public void open() {
<<<<<<< HEAD
        this.signupView.setVisible(true);
    }

    public void close() {
        this.signupView.dispose();
=======
        userView.setVisible(true);
    }

    public void close() {
        userView.dispose();
>>>>>>> main
    }

    // 🔹 When user clicks "Login" button on Signup page
    private class LoginListener implements ActionListener {

        private Dashboard view;
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginView = new Login();
            close();
<<<<<<< HEAD
            LoginController controller = new LoginController(loginView, view);      // Attach controller
=======
            LoginController controller = new LoginController(loginView);      // Attach controller
>>>>>>> main
            controller.open();  
            
        }
    }

    // 🔹 When user clicks "Signup" button
    private class SignUpListener implements ActionListener {

        private boolean check;
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
<<<<<<< HEAD
                if(check) {
                    JOptionPane.showMessageDialog(signupView, "User already exists!");
=======
                if (userDao.check(usermodel)) {
                    JOptionPane.showMessageDialog(userView, "User already exists!");
>>>>>>> main
                    return;
                }else{

                // Save user
                userDao.signup(usermodel);
<<<<<<< HEAD
                JOptionPane.showMessageDialog(signupView, "Signup successful!");        // ✓ Success message
                }
                
=======
                JOptionPane.showMessageDialog(userView, "Signup successful!");        // ✓ Success message
                Login loginView = new Login();
                close();
                LoginController controller = new LoginController(loginView);      // Attach controller
                controller.open();               

>>>>>>> main
            } catch (Exception ex) {
                ex.printStackTrace(); // optional for debugging
                JOptionPane.showMessageDialog(userView, "Signup failed!");
            }
        }
    }
}
         

      

