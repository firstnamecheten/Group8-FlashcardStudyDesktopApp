package view;

import database.MySqlConnection;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;

public class AdminDashboard extends JFrame {
    
    private static final java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(AdminDashboard.class.getName());

    
    



    // ✅ Constructor now requires Login reference
    public AdminDashboard() {
        setSize(1285, 760);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // ================= HEADER WITH LOGO =================
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(Color.WHITE);
//
//        ImageIcon logoIcon = null;
//        try {
//            logoIcon = new ImageIcon(getClass().getResource("/122.png"));
//        } catch (Exception e) {
//            System.out.println("Logo image not found or failed to load.");
//        }
//
//        if (logoIcon != null && logoIcon.getIconWidth() > 0) {
//            JLabel logoLabel = new JLabel(logoIcon);
//            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
//            headerPanel.add(logoLabel, BorderLayout.CENTER);
//        } else {
//            JLabel fallbackLabel = new JLabel("Admin Dashboard");
//            fallbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
//            fallbackLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
//            headerPanel.add(fallbackLabel, BorderLayout.CENTER);
//        }
//
//        add(headerPanel, BorderLayout.NORTH);
//
//        // ================= TABLES INSIDE TABS =================
//        signupTable = new JTable();
//        loginTable = new JTable();
//        logoutTable = new JTable();
//        deckTable = new JTable();
//        flashcardTable = new JTable();
//
//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.add("Signup History", new JScrollPane(signupTable));
//        tabbedPane.add("Login History", new JScrollPane(loginTable));
//        tabbedPane.add("Logout History", new JScrollPane(logoutTable));
//        tabbedPane.add("Decks", new JScrollPane(deckTable));
//        tabbedPane.add("Flashcards", new JScrollPane(flashcardTable));
//
//        add(tabbedPane, BorderLayout.CENTER);
//
//        // ================= BUTTONS AT THE BOTTOM =================
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setBackground(Color.WHITE);
//
//        loadSignupButton = new JButton("Load Signup History");
//        loadLoginButton = new JButton("Load Login History");
//        loadLogoutButton = new JButton("Load Logout History");
//        loadDeckButton = new JButton("Load Decks");
//        loadFlashcardButton = new JButton("Load Flashcards");
//        logoutButton = new JButton("Logout");
//
//        Color blue = new Color(0, 120, 215);
//        for (JButton btn : new JButton[]{
//                loadSignupButton, loadLoginButton, loadLogoutButton,
//                loadDeckButton, loadFlashcardButton, logoutButton
//        }) {
//            btn.setBackground(blue);
//            btn.setForeground(Color.WHITE);
//            btn.setFocusPainted(false);
//            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
//            buttonPanel.add(btn);
//        }
//
//        add(buttonPanel, BorderLayout.SOUTH);
//
//        // ================= LISTENERS =================
//        loadSignupButton.addActionListener(e -> loadSignupTable());
//        loadLoginButton.addActionListener(e -> loadLoginTable());
//        loadLogoutButton.addActionListener(e -> loadLogoutTable());
//        loadDeckButton.addActionListener(e -> loadDeckTable());
//        loadFlashcardButton.addActionListener(e -> loadFlashcardTable());
//
//        logoutButton.addActionListener(e -> {
//            loginView.setVisible(true);   // ✅ show login again
//            this.setVisible(false);       // ✅ hide admin dashboard
//        });
//
//        // Mask password column in signupTable
//        signupTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                                                           boolean isSelected, boolean hasFocus, int row, int column) {
//                if (column == 2) { // password column index
//                    value = "********";
//                }
//                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            }
//        });
//        // Mask password + confirm password in signupTable
//        signupTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value,
//                                                   boolean isSelected, boolean hasFocus, int row, int column) {
//        if (column == 2 || column == 3) { // password + confirm password columns
//            value = "********";
//        }
//        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//        }
//        });
//        // Mask password in loginTable
//        loginTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value,
//                                                   boolean isSelected, boolean hasFocus, int row, int column) {
//        if (column == 3) { // password column index
//            value = "********";
//        }
//        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//        }
//        });
//    }
//
//
//
//    // ================= LOAD METHODS =================
//    private void loadSignupTable() {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new String[]{"user_id", "username", "password", "confirmpassword"});
//        try (Connection conn = mysql.openConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT user_id, username, password, confirmpassword FROM signup_history")) {
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                        rs.getInt("user_id"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getString("confirmpassword")
//                });
//            }
//            signupTable.setModel(model);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void loadLoginTable() {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new String[]{"login_id", "user_id", "username", "password", "login_time"});
//        try (Connection conn = mysql.openConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT login_id, user_id, username, password, login_time FROM login_history")) {
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                        rs.getInt("login_id"),
//                        rs.getInt("user_id"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getTimestamp("login_time")
//                });
//            }
//            loginTable.setModel(model);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void loadLogoutTable() {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new String[]{"logout_id", "login_id", "user_id", "session_id", "logout_time"});
//        try (Connection conn = mysql.openConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT logout_id, login_id, user_id, session_id, logout_time FROM logout_history")) {
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                        rs.getInt("logout_id"),
//                        rs.getInt("login_id"),
//                        rs.getInt("user_id"),
//                        rs.getString("session_id"),
//                        rs.getTimestamp("logout_time")
//                });
//            }
//            logoutTable.setModel(model);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void loadDeckTable() {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new String[]{"deck_id", "user_id", "deck_name", "created_at"});
//        try (Connection conn = mysql.openConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT deck_id, user_id, deck_name, created_at FROM decks")) {
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                        rs.getInt("deck_id"),
//                        rs.getInt("user_id"),
//                        rs.getString("deck_name"),
//                        rs.getTimestamp("created_at")
//                });
//            }
//            deckTable.setModel(model);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void loadFlashcardTable() {
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new String[]{"card_id", "deck_id", "user_id", "created_at"});
//        try (Connection conn = mysql.openConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT card_id, deck_id, user_id, created_at FROM flashcards")) {
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                        rs.getInt("card_id"),
//                        rs.getInt("deck_id"),
//                        rs.getInt("user_id"),
//                        rs.getTimestamp("created_at")
//                });
//            }
//            flashcardTable.setModel(model);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel1 = new javax.swing.JPanel();
        Logo_label = new javax.swing.JLabel();
        scrollbar2 = new java.awt.Scrollbar();
        Home_Label = new javax.swing.JLabel();
        LoadLogoutButton = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        SignupTable = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        SignupTable4 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        DeckTable = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        SignupTable2 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        SignupTable3 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        LoadSignupButton = new javax.swing.JButton();
        LoadDeckButton = new javax.swing.JButton();
        LoadLoginButton = new javax.swing.JButton();
        LoadFlashcardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1285, 760));
        getContentPane().setLayout(null);

        topPanel1.setBackground(new java.awt.Color(255, 255, 255));
        topPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Logo_label.setBackground(new java.awt.Color(254, 254, 254));
        Logo_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/122.png"))); // NOI18N

        Home_Label.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Home_Label.setText("Admin Dashboard");

        javax.swing.GroupLayout topPanel1Layout = new javax.swing.GroupLayout(topPanel1);
        topPanel1.setLayout(topPanel1Layout);
        topPanel1Layout.setHorizontalGroup(
            topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanel1Layout.createSequentialGroup()
                .addContainerGap(544, Short.MAX_VALUE)
                .addComponent(Logo_label, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Home_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(464, 464, 464))
        );
        topPanel1Layout.setVerticalGroup(
            topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanel1Layout.createSequentialGroup()
                .addComponent(Logo_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(topPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Home_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        getContentPane().add(topPanel1);
        topPanel1.setBounds(0, 0, 1330, 70);

        LoadLogoutButton.setBackground(new java.awt.Color(0, 153, 255));
        LoadLogoutButton.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LoadLogoutButton.setForeground(new java.awt.Color(255, 255, 255));
        LoadLogoutButton.setText("Load");
        LoadLogoutButton.addActionListener(this::LoadLogoutButtonActionPerformed);
        getContentPane().add(LoadLogoutButton);
        LoadLogoutButton.setBounds(1160, 330, 72, 23);

        jButton3.setBackground(new java.awt.Color(0, 153, 255));
        jButton3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Logout");
        getContentPane().add(jButton3);
        jButton3.setBounds(1132, 660, 100, 26);

        SignupTable.setBackground(new java.awt.Color(0, 153, 255));
        SignupTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        SignupTable.setText("                                Signup_history Table");
        SignupTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        getContentPane().add(SignupTable);
        SignupTable.setBounds(30, 120, 380, 20);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "user_id", "username", "password", "confirmpassword"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setMinWidth(60);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable3.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable3.getColumnModel().getColumn(1).setMinWidth(60);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable3.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        jScrollPane6.setViewportView(jScrollPane7);

        getContentPane().add(jScrollPane6);
        jScrollPane6.setBounds(30, 140, 380, 180);

        SignupTable4.setBackground(new java.awt.Color(0, 153, 255));
        SignupTable4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        SignupTable4.setText("                                      Logout Table");
        SignupTable4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        getContentPane().add(SignupTable4);
        SignupTable4.setBounds(850, 120, 380, 20);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "logout_id", "user_id", "login_id", "session_id", "logout_time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane15.setViewportView(jTable7);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(0).setMinWidth(60);
            jTable7.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable7.getColumnModel().getColumn(1).setMinWidth(60);
            jTable7.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(1).setMaxWidth(60);
            jTable7.getColumnModel().getColumn(2).setMinWidth(60);
            jTable7.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(2).setMaxWidth(60);
        }

        jScrollPane14.setViewportView(jScrollPane15);

        getContentPane().add(jScrollPane14);
        jScrollPane14.setBounds(850, 140, 380, 180);

        DeckTable.setBackground(new java.awt.Color(0, 153, 255));
        DeckTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        DeckTable.setText("                                      Deck Table");
        DeckTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        getContentPane().add(DeckTable);
        DeckTable.setBounds(30, 380, 380, 20);

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "deck_id", "user_id", "deck_name", "created_at"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setMinWidth(60);
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable4.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable4.getColumnModel().getColumn(1).setMinWidth(60);
            jTable4.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable4.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        jScrollPane8.setViewportView(jScrollPane9);

        getContentPane().add(jScrollPane8);
        jScrollPane8.setBounds(30, 400, 380, 180);

        SignupTable2.setBackground(new java.awt.Color(0, 153, 255));
        SignupTable2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        SignupTable2.setText("                               Login_history Table");
        SignupTable2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        getContentPane().add(SignupTable2);
        SignupTable2.setBounds(440, 120, 380, 20);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "login_id", "user_id", "username", "password", "login_time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane11.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setMinWidth(60);
            jTable5.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable5.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable5.getColumnModel().getColumn(1).setMinWidth(60);
            jTable5.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable5.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        jScrollPane10.setViewportView(jScrollPane11);

        getContentPane().add(jScrollPane10);
        jScrollPane10.setBounds(440, 140, 380, 180);

        SignupTable3.setBackground(new java.awt.Color(0, 153, 255));
        SignupTable3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        SignupTable3.setText("                                 Flashcard Table");
        SignupTable3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        getContentPane().add(SignupTable3);
        SignupTable3.setBounds(440, 380, 380, 20);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "card_id", "deck_id", "user_id", "created_at"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane13.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(0).setMinWidth(60);
            jTable6.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable6.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable6.getColumnModel().getColumn(1).setMinWidth(60);
            jTable6.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable6.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        jScrollPane12.setViewportView(jScrollPane13);

        getContentPane().add(jScrollPane12);
        jScrollPane12.setBounds(440, 400, 380, 180);

        LoadSignupButton.setBackground(new java.awt.Color(0, 153, 255));
        LoadSignupButton.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LoadSignupButton.setForeground(new java.awt.Color(255, 255, 255));
        LoadSignupButton.setText("Load");
        LoadSignupButton.addActionListener(this::LoadSignupButtonActionPerformed);
        getContentPane().add(LoadSignupButton);
        LoadSignupButton.setBounds(340, 330, 72, 23);

        LoadDeckButton.setBackground(new java.awt.Color(0, 153, 255));
        LoadDeckButton.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LoadDeckButton.setForeground(new java.awt.Color(255, 255, 255));
        LoadDeckButton.setText("Load");
        LoadDeckButton.addActionListener(this::LoadDeckButtonActionPerformed);
        getContentPane().add(LoadDeckButton);
        LoadDeckButton.setBounds(340, 590, 72, 23);

        LoadLoginButton.setBackground(new java.awt.Color(0, 153, 255));
        LoadLoginButton.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LoadLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        LoadLoginButton.setText("Load");
        LoadLoginButton.addActionListener(this::LoadLoginButtonActionPerformed);
        getContentPane().add(LoadLoginButton);
        LoadLoginButton.setBounds(750, 330, 72, 23);

        LoadFlashcardButton.setBackground(new java.awt.Color(0, 153, 255));
        LoadFlashcardButton.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LoadFlashcardButton.setForeground(new java.awt.Color(255, 255, 255));
        LoadFlashcardButton.setText("Load");
        LoadFlashcardButton.addActionListener(this::LoadFlashcardButtonActionPerformed);
        getContentPane().add(LoadFlashcardButton);
        LoadFlashcardButton.setBounds(750, 590, 72, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoadSignupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadSignupButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoadSignupButtonActionPerformed

    private void LoadLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadLoginButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoadLoginButtonActionPerformed

    private void LoadLogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadLogoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoadLogoutButtonActionPerformed

    private void LoadDeckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadDeckButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoadDeckButtonActionPerformed

    private void LoadFlashcardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadFlashcardButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoadFlashcardButtonActionPerformed

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DeckTable;
    private javax.swing.JLabel Home_Label;
    private javax.swing.JButton LoadDeckButton;
    private javax.swing.JButton LoadFlashcardButton;
    private javax.swing.JButton LoadLoginButton;
    private javax.swing.JButton LoadLogoutButton;
    private javax.swing.JButton LoadSignupButton;
    private javax.swing.JLabel Logo_label;
    private javax.swing.JLabel SignupTable;
    private javax.swing.JLabel SignupTable2;
    private javax.swing.JLabel SignupTable3;
    private javax.swing.JLabel SignupTable4;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private java.awt.Scrollbar scrollbar2;
    private javax.swing.JPanel topPanel1;
    // End of variables declaration//GEN-END:variables

    public void LoadSignupButtonListener(ActionListener listener){
        LoadSignupButton.addActionListener(listener);
    }
    public void LoadLoginButtonListener(ActionListener listener){
        LoadLoginButton.addActionListener(listener);
    }
    public void LoadButtonListener(ActionListener listener){
        LoadLogoutButton.addActionListener(listener);
    }
    public void LoadDeckButtonListener(ActionListener listener){
        LoadDeckButton.addActionListener(listener);
    }
    public void LoadFlashcardButtonListener(ActionListener listener){
        LoadFlashcardButton.addActionListener(listener);
    }
    
}
