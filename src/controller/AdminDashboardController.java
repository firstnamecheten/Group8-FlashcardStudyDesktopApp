package controller;

import database.MySqlConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import view.AdminDashboard;
import view.Login;

public class AdminDashboardController {

    private final MySqlConnection mysql = new MySqlConnection();
    private final Login log;
    private final AdminDashboard adminDashboardView;

    // Tables
    private JTable signupTable;
    private JTable loginTable;
    private JTable logoutTable;
    private JTable deckTable;
    private JTable flashcardTable;

    // Buttons
    private JButton loadSignupButton;
    private JButton loadLoginButton;
    private JButton loadLogoutButton;
    private JButton loadDeckButton;
    private JButton loadFlashcardButton;
    private JButton logoutButton;

    private static final Logger logger = Logger.getLogger(AdminDashboardController.class.getName());

    public AdminDashboardController(Login log, AdminDashboard adminDashboardView) {
        this.log = log;
        this.adminDashboardView = adminDashboardView;

        adminDashboardView.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        ImageIcon logoIcon = null;
        try {
            logoIcon = new ImageIcon(getClass().getResource("/122.png"));
        } catch (Exception e) {
            System.out.println("Logo image not found or failed to load.");
        }

        if (logoIcon != null && logoIcon.getIconWidth() > 0) {
            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(logoLabel, BorderLayout.CENTER);
        } else {
            JLabel fallbackLabel = new JLabel("Admin Dashboard");
            fallbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
            fallbackLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            headerPanel.add(fallbackLabel, BorderLayout.CENTER);
        }
        adminDashboardView.add(headerPanel, BorderLayout.NORTH);

        // Tables
        signupTable = new JTable();
        loginTable = new JTable();
        logoutTable = new JTable();
        deckTable = new JTable();
        flashcardTable = new JTable();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Signup History", new JScrollPane(signupTable));
        tabbedPane.add("Login History", new JScrollPane(loginTable));
        tabbedPane.add("Logout History", new JScrollPane(logoutTable));
        tabbedPane.add("Decks", new JScrollPane(deckTable));
        tabbedPane.add("Flashcards", new JScrollPane(flashcardTable));
        adminDashboardView.add(tabbedPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        loadSignupButton = new JButton("Load Signup History");
        loadLoginButton = new JButton("Load Login History");
        loadLogoutButton = new JButton("Load Logout History");
        loadDeckButton = new JButton("Load Decks");
        loadFlashcardButton = new JButton("Load Flashcards");
        logoutButton = new JButton("Logout");

        Color blue = new Color(0, 120, 215);
        for (JButton btn : new JButton[]{
                loadSignupButton, loadLoginButton, loadLogoutButton,
                loadDeckButton, loadFlashcardButton, logoutButton
        }) {
            btn.setBackground(blue);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            buttonPanel.add(btn);
        }

        adminDashboardView.add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        loadSignupButton.addActionListener(e -> loadSignupTable());
        loadLoginButton.addActionListener(e -> loadLoginTable());
        loadLogoutButton.addActionListener(e -> loadLogoutTable());
        loadDeckButton.addActionListener(e -> loadDeckTable());
        loadFlashcardButton.addActionListener(e -> loadFlashcardTable());
        logoutButton.addActionListener(e -> {
            log.setVisible(true);
            adminDashboardView.setVisible(false);
        });

        // Mask password columns
        signupTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 2 || column == 3) { // password + confirm password
                    value = "********";
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        loginTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 3) { // password column
                    value = "********";
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
    }

    // Load methods
    private void loadSignupTable() {
        DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 🔒 no cell can be edited
    }
};
        model.setColumnIdentifiers(new String[]{"user_id", "username", "password", "confirmpassword"});
        try (Connection conn = mysql.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT user_id, username, password, confirmpassword FROM signup_history")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("confirmpassword")
                });
            }
            signupTable.setModel(model);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void loadLoginTable() {
        DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 🔒 no cell can be edited
    }
};
        model.setColumnIdentifiers(new String[]{"login_id", "user_id", "username", "password", "login_time"});
        try (Connection conn = mysql.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT login_id, user_id, username, password, login_time FROM login_history")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("login_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("login_time")
                });
            }
            loginTable.setModel(model);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void loadLogoutTable() {
        DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 🔒 no cell can be edited
    }
};
        model.setColumnIdentifiers(new String[]{"logout_id", "login_id", "user_id", "session_id", "logout_time"});
        try (Connection conn = mysql.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT logout_id, login_id, user_id, session_id, logout_time FROM logout_history")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("logout_id"),
                        rs.getInt("login_id"),
                        rs.getInt("user_id"),
                        rs.getString("session_id"),
                        rs.getTimestamp("logout_time")
                });
            }
            logoutTable.setModel(model);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void loadDeckTable() {
        DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 🔒 no cell can be edited
    }
};
        model.setColumnIdentifiers(new String[]{"deck_id", "user_id", "deck_name", "created_at"});
        try (Connection conn = mysql.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT deck_id, user_id, deck_name, created_at FROM decks")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("deck_id"),
                        rs.getInt("user_id"),
                        rs.getString("deck_name"),
                        rs.getTimestamp("created_at")
                });
            }
            deckTable.setModel(model);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadFlashcardTable() {
    DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 🔒 no cell can be edited
    }
};
    model.setColumnIdentifiers(new String[]{"card_id", "deck_id", "user_id", "front_text", "back_text", "created_at"});

    try (Connection conn = mysql.openConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(
             "SELECT card_id, deck_id, user_id, front_text, back_text, created_at FROM flashcards"
         )) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("card_id"),
                rs.getInt("deck_id"),
                rs.getInt("user_id"),
                rs.getString("front_text"),
                rs.getString("back_text"),
                rs.getTimestamp("created_at")
            });
        }
        flashcardTable.setModel(model);
    } catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
    }
}
}