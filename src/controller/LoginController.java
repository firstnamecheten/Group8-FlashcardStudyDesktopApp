package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.UserModel;
import view.AdminDashboard;
import view.Dashtwo;
import view.Login;
import view.Signup;
public class LoginController {

    private final Login log;
    private final Signup signupView;
    private final UserDao userDao = new UserDao();

//    private final AdminDashboard adminDashboardView; // ✅ add admin dashboard

    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 5;
    private long lockoutEndTime = 0;
    private boolean forgotInProgress = false;

    public LoginController(Login log, Signup signupView) {
          this.log = log;
//        this.dashtwoView = dashtwoView;
          this.signupView = signupView;
//        this.adminDashboardView = adminDashboardView;

        log.LoginButtonListener(new LoginButtonListener());
        log.ForgotPasswordButtonListener(new ForgotPasswordButtonListener());
        log.CreateAccountButtonListener(new CreateAccountButtonListener());
    }

    public void open() { log.setVisible(true); }
    public void close() { log.dispose(); }

    private void setLoginEnabled(boolean enabled) {
        log.getUsernameField().setEnabled(enabled);
        log.getPasswordField().setEnabled(enabled);
        log.getLoginButton().setEnabled(enabled);
    }

    // ================= LOGIN LOGIC =================
    private UserModel tryLogin(String username, String password) {
        long now = System.currentTimeMillis();
        if (now < lockoutEndTime) {
            long remainingSeconds = (lockoutEndTime - now) / 1000;
            JOptionPane.showMessageDialog(
                    log,
                    "Too many attempts! Try again in " + remainingSeconds + " seconds."
            );
            return null;
        }

        UserModel user = userDao.login(username, password);

        if (user != null) {
            loginAttempts = 0;
            return user; // ✅ only return, no popup here
        }

        loginAttempts++;
        if (loginAttempts >= MAX_ATTEMPTS) {
            lockoutEndTime = System.currentTimeMillis() + (5 * 60 * 1000);
            setLoginEnabled(false);

            JOptionPane.showMessageDialog(
                    log,
                    "Too many wrong attempts! Login disabled for 5 minutes."
            );

            new Timer(5 * 60 * 1000, e -> {
                setLoginEnabled(true);
                loginAttempts = 0;
                lockoutEndTime = 0;
                JOptionPane.showMessageDialog(log, "You can try logging in again now.");
            }).start();

        } else {
            JOptionPane.showMessageDialog(
                    log,
                    "Wrong username or password! Attempt " + loginAttempts + " of " + MAX_ATTEMPTS
            );
        }

        return null;
    }

    // ================= LOGIN BUTTON =================
    class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = log.getUsernameField().getText().trim();
        String password = log.getPasswordField().getText().trim();

        if (username.equalsIgnoreCase("Enter the username")) username = "";
        if (password.equalsIgnoreCase("Enter the password")) password = "";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(log, "All fields are required!");
            return;
        }

        // ✅ Check for Admin login first
        // ✅ Check for Admin login first
    if (username.equals("Admin") && password.equals("1234")) {
    JOptionPane.showMessageDialog(log, "Admin login successful!");

    // ✅ Create the AdminDashboard view
    AdminDashboard adminDashboardView = new AdminDashboard();

    // ✅ Attach the controller to wire tables/buttons
    AdminDashboardController adminController =
            new AdminDashboardController(log, adminDashboardView);

    // ✅ Show the dashboard
    adminDashboardView.setVisible(true);

    // Hide login window
    log.setVisible(false);
    return;
}

        // ✅ Otherwise, normal user login
UserModel user = tryLogin(username, password);

if (user != null) {
    JOptionPane.showMessageDialog(log, "Login successful!");

    // ✅ Capture loginId when inserting login history
    int loginId = userDao.insertLoginHistory(user.getUserId(), user.getUsername(), password);

    // ✅ Store loginId in UserModel so DashtwoController can use it later
    user.setLoginId(loginId);

    Dashtwo dash = new Dashtwo();
    close();
    DashtwoController dashCon = new DashtwoController(dash, log);
    dashCon.setCurrentUser(user);   // pass currentUser with loginId set
    dashCon.open();
}
}
}


    // ================= FORGOT PASSWORD BUTTON =================
    class ForgotPasswordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (forgotInProgress) return;

            forgotInProgress = true;
            try {
                handleForgotPassword();
            } finally {
                forgotInProgress = false;
            }
        }
    }

    // ================= CREATE ACCOUNT =================
    public class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            signupView.setVisible(true); // reopen original signup page
         
        }
    }

    // ================= FORGOT PASSWORD LOGIC =================
    private void handleForgotPassword() {
        JTextField usernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "New Password:", newPasswordField
        };

        int option = JOptionPane.showConfirmDialog(
                log,
                message,
                "Forgot Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        String username = usernameField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();

        if (username.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(log, "All fields are required!");
            return;
        }

        UserModel user = userDao.getUserByUsername(username);

        if (user == null) {
            JOptionPane.showMessageDialog(log, "Username not found!");
            return;
        }

        user.setPassword(newPassword);
        userDao.updatePassword(user);

        JOptionPane.showMessageDialog(log, "Password updated successfully!");
    }
}
