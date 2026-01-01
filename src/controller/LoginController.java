package controller;

import dao.LoginDao;
import dao.UserDao;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.LoginModel;
import model.UserModel;
import view.Dashboard;
import view.Login;
import view.Signup;
import view.AdminDashboard;   // ✅ import AdminDashboard

public class LoginController {

    private final Login loginView;
    private final UserDao userDao = new UserDao();
<<<<<<< HEAD
//    private final Dashboard dashtwoView;
=======
  
>>>>>>> main
//    private final Signup signupView;
//    private final AdminDashboard adminDashboardView; // ✅ add admin dashboard

    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;
    private long lockoutEndTime = 0;
    private boolean forgotInProgress = false;
<<<<<<< HEAD
    private final Dashboard view;
=======
    
    private final LoginDao loginDao = new LoginDao();
  
>>>>>>> main

    public LoginController(Login loginView, Dashboard view) {
        this.loginView = loginView;
<<<<<<< HEAD
        this.view = view;
//        this.signupView = signupView;
//        this.adminDashboardView = adminDashboardView;
=======
        
        
       

>>>>>>> main

        loginView.LoginButtonListener(new LoginButtonListener());
//        loginView.ForgotPasswordButtonListener(new ForgotPasswordButtonListener());
//        loginView.CreateAccountButtonListener(new CreateAccountButtonListener());
    }

    public void open() { 
<<<<<<< HEAD
        this.loginView.setVisible(true); 
    }
    public void close() { 
        this.loginView.dispose(); 
=======
        loginView.setVisible(true); 
    }
    public void close() { 
        loginView.dispose(); 
>>>>>>> main
    }

    private void setLoginEnabled(boolean enabled) {
        loginView.getUsernameField().setEnabled(enabled);
        loginView.getPasswordField().setEnabled(enabled);
        loginView.getLoginButton().setEnabled(enabled);
    }

    // ================= LOGIN LOGIC =================
    private LoginModel tryLogin(String username, String password) {
        long now = System.currentTimeMillis();
        if (now < lockoutEndTime) {
            long remainingSeconds = (lockoutEndTime - now) / 1000;
            JOptionPane.showMessageDialog(
                    loginView,
                    "Too many attempts! Try again in " + remainingSeconds + " seconds."
            );
            return null;
        }

        LoginModel loginmodel = loginDao.login(username, password);

        if (loginmodel != null) {
            loginAttempts = 0;
            return loginmodel; // ✅ only return, no popup here
        }

        loginAttempts++;
        if (loginAttempts >= MAX_ATTEMPTS) {
            lockoutEndTime = System.currentTimeMillis() + (5 * 60 * 1000);
            setLoginEnabled(false);

            JOptionPane.showMessageDialog(
                    loginView,
                    "Too many wrong attempts! Login disabled for 5 minutes."
            );

            new Timer(5 * 60 * 1000, e -> {
                setLoginEnabled(true);
                loginAttempts = 0;
                lockoutEndTime = 0;
                JOptionPane.showMessageDialog(loginView, "You can try logging in again now.");
            }).start();

        } else {
            JOptionPane.showMessageDialog(
                    loginView,
                    "Wrong username or password! Attempt " + loginAttempts + " of " + MAX_ATTEMPTS
            );
        }

        return null;
    }

    // ================= LOGIN BUTTON =================
class LoginButtonListener implements ActionListener {

        private Component adminDashboardView;
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
<<<<<<< HEAD

            // ✅ Check for Admin login first
            if (username.equals("Admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(loginView, "Admin login successful!");
                AdminDashboard admindashboardView = new AdminDashboard();
                close();
 //               AdminDashboardController adminDashboardView = new AdminDashboardController(adminDashboardView);
 //               adminDashboardView.open();
            }

            // ✅ Otherwise, normal user login
            UserModel user = tryLogin(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(loginView, "Login successful!");
                userDao.insertLoginHistory(user.getUserId(), user.getUsername(), password);
              Dashboard view = new Dashboard();
              close();
              DashboardController dashCon = new DashboardController(view);
              dashCon.open();
            }
        }
=======
        
        // ✅ Otherwise, normal user login
        LoginModel loginmodel = tryLogin(username, password); // ✅ Use actual login check
        if (loginmodel != null) {
        JOptionPane.showMessageDialog(loginView, "Login successful!");
        loginDao.insertLoginHistory(loginmodel); // ✅ Log successful login
        Dashboard view = new Dashboard();
        close();
        DashboardController dashCon = new DashboardController(view);
        dashCon.open();
}
              
//            
//            // Admin Dahboard action perfomed
//            if (username.equals("Admin") && password.equals("1234")) {
//            JOptionPane.showMessageDialog(adminDashboardView, "Admin Login successful!");
//            AdminDashboard asminDashboardView = new AdminDashboard();
//            close(); // close current login/signup window
//            LoginController adcontroller = new LoginController(loginView); 
//            adcontroller.open(); // open the Admin Dashboard
//
//        }  
>>>>>>> main
    }


//    // ================= FORGOT PASSWORD BUTTON =================
//    class ForgotPasswordButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (forgotInProgress) return;
//
//            forgotInProgress = true;
//            try {
//                handleForgotPassword();
//            } finally {
//                forgotInProgress = false;
//            }
//        }
//    }

    // ================= CREATE ACCOUNT =================
    class CreateAccountButtonListener implements ActionListener {
       
      public void actionPerformed(ActionEvent e) {
            Signup signupView = new Signup();
            close();
            UserController uc = new UserController(signupView);      // Attach controller
            uc.open(); 
           
        }
   }

//    // ================= FORGOT PASSWORD LOGIC =================
//    private void handleForgotPassword() {
//        JTextField usernameField = new JTextField();
//        JPasswordField newPasswordField = new JPasswordField();
//
//        Object[] message = {
//                "Username:", usernameField,
//                "New Password:", newPasswordField
//        };
//
//        int option = JOptionPane.showConfirmDialog(
//                loginView,
//                message,
//                "Forgot Password",
//                JOptionPane.OK_CANCEL_OPTION,
//                JOptionPane.PLAIN_MESSAGE
//        );
//
//        if (option != JOptionPane.OK_OPTION) return;
//
//        String username = usernameField.getText().trim();
//        String newPassword = new String(newPasswordField.getPassword()).trim();
//
//        if (username.isEmpty() || newPassword.isEmpty()) {
//            JOptionPane.showMessageDialog(loginView, "All fields are required!");
//            return;
//        }
//
//        UserModel user = userDao.getUserByUsername(username);
//
//        if (user == null) {
//            JOptionPane.showMessageDialog(loginView, "Username not found!");
//            return;
//        }
//
//        user.setPassword(newPassword);
//        userDao.updatePassword(user);
//
//        JOptionPane.showMessageDialog(loginView, "Password updated successfully!");
//    
//    }
   }
}
