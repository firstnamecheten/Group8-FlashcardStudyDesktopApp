package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserModel;
import view.Signup;
import view.Login;

public class UserController {

    private final UserDao userdao = new UserDao();
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

    // ✅ When user clicks "Login" button
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginView = new Login();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            userView.dispose();
        }
    }

    // ✅ When user clicks "Sign Up" button
    class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // ✅ Get text safely
                String username = userView.getUsernameField().getText();
                String email = userView.getEmailField().getText();
                String password = userView.getPasswordField().getText();
                String confirmPassword = userView.getConfirmPasswordField().getText();

                 // ✅ Normalize null → ""
                username = username == null ? "" : username.trim();
                email = email == null ? "" : email.trim();
                password = password == null ? "" : password.trim();
                confirmPassword = confirmPassword == null ? "" : confirmPassword.trim();

                // ✅ Required fields check FIRST
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(userView, "All fields are required!");
                return;
}

                // ✅ Password match check SECOND
                if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(userView, "Passwords do not match!");
                return;
}

                // ✅ Create model
                UserModel usermodel = new UserModel(username.trim(), email.trim(), password.trim(), confirmPassword.trim());

                // ✅ Check if user already exists
                if (userdao.check(usermodel)) {
                    JOptionPane.showMessageDialog(userView, "User already exists!");
                    return;
                }

                // ✅ Save user
                userdao.signUp(usermodel);

                // ✅ Success message
                JOptionPane.showMessageDialog(userView, "Signup successful!");

                // ✅ Redirect to login page
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

    // ✅ Helper method for clean validation
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
