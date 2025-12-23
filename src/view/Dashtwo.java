package view;

import controller.LoginController;
import model.UserModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Dashtwo extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Dashtwo.class.getName());

    // ✅ Track the currently active dashboard
    private static Dashtwo ACTIVE_DASHBOARD;
    
    private boolean isDarkMode;

    // Keep track of the current logged-in user
    private final UserModel currentUser;

    /** Creates new form Dashtwo (preferred: with user context) */
    public Dashtwo(UserModel user) {
        initComponents();
        setSize(1285, 760);
        this.currentUser = user;
        ACTIVE_DASHBOARD = this; // ✅ track this dashboard
        wireMenuActions();
    }

    /** No-arg constructor (needed for GUI builder or fallback) */
    public Dashtwo() {
        initComponents();
        setSize(1285, 760);
        this.currentUser = null; // fallback
        ACTIVE_DASHBOARD = this; // ✅ track this dashboard
        wireMenuActions();
    }

    // ✅ Getter for the active dashboard
    public static Dashtwo getActiveDashboard() {
        return ACTIVE_DASHBOARD;
    }
    
    /** Centralized wiring of menu actions to avoid duplication */
    private void wireMenuActions() {
        isDarkMode = false;

        if (darkModeMenuItem != null) darkModeMenuItem.addActionListener(e -> toggleDarkMode());
        if (fontSizeMenuItem != null) fontSizeMenuItem.addActionListener(e -> showFontSizeOptions());
        if (studyHistoryMenuItem != null) studyHistoryMenuItem.addActionListener(e -> openStudyHistory());
        if (logoutMenuItem != null) logoutMenuItem.addActionListener(e -> logout());
        if (accountMenuItem != null) accountMenuItem.addActionListener(e -> openUserBasedFlashcardOwnership());
    }

    // === Navigation/actions ===

    private void openUserBasedFlashcardOwnership() {
    if (accountPopupMenu != null) {
        accountPopupMenu.setVisible(false);
    }

    UserBasedFlashcardOwnership page = new UserBasedFlashcardOwnership(currentUser, this);
    page.setVisible(true);
    this.setVisible(false); // ✅ hide dashboard while account page is open
}

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                Login loginView = new Login();
                LoginController controller = new LoginController(loginView);
                controller.open();
            });
        }
    }

    // === UI behavior ===

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;

        if (isDarkMode) {
            getContentPane().setBackground(new java.awt.Color(30, 30, 30));
            topPanel1.setBackground(new java.awt.Color(40, 40, 40));
            centerpanel.setBackground(new java.awt.Color(45, 45, 45));

            Home_Button.setForeground(new java.awt.Color(200, 200, 200));
            Home_Button.setBackground(new java.awt.Color(45, 45, 45)); // dark background
            Home_Button.setOpaque(true);
            Library_Button.setForeground(new java.awt.Color(200, 200, 200));
            Library_Button.setBackground(new java.awt.Color(45, 45, 45));
            Library_Button.setOpaque(true);

            Home_Label.setForeground(new java.awt.Color(255, 255, 255));
            Home_Label.setBackground(new java.awt.Color(45, 45, 45));
            Logo_label.setBackground(new java.awt.Color(45, 45, 45));
            QuickStart_label.setForeground(new java.awt.Color(255, 255, 255));
            QuickStart_label.setBackground(new java.awt.Color(45, 45, 45));
            Create_Button.setForeground(new java.awt.Color(255, 255, 255));
            Create_Button.setBackground(new java.awt.Color(40, 40, 40));

            darkModeMenuItem.setText(" Light mode");
        } else {
            getContentPane().setBackground(new java.awt.Color(240, 240, 240));
            topPanel1.setBackground(new java.awt.Color(255, 255, 255));
            centerpanel.setBackground(new java.awt.Color(255, 255, 255));

            Home_Button.setForeground(new java.awt.Color(0, 0, 0));
            Home_Button.setBackground(java.awt.Color.WHITE);
            Home_Button.setOpaque(true);
            Library_Button.setForeground(new java.awt.Color(0, 0, 0));
            Library_Button.setBackground(java.awt.Color.WHITE);
            Library_Button.setOpaque(true);
            Home_Label.setForeground(new java.awt.Color(0, 0, 0));
            Home_Label.setBackground(new java.awt.Color(240, 240, 240));
            QuickStart_label.setForeground(new java.awt.Color(0, 0, 0));
            QuickStart_label.setBackground(java.awt.Color.WHITE);
            Create_Button.setForeground(new java.awt.Color(0, 0, 0));
            Create_Button.setBackground(new java.awt.Color(240, 240, 240));
            Create_Button.setOpaque(true);

            darkModeMenuItem.setText(" Dark mode");
        }

        repaint();
        revalidate();
    }

    private void showFontSizeOptions() {
        String[] options = {"Small", "Medium", "Large"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose font size",
                "Font Size",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]
        );

        java.awt.Font current = Home_Label.getFont();
        switch (choice) {
            case 0 -> Home_Label.setFont(current.deriveFont(14f));
            case 1 -> Home_Label.setFont(current.deriveFont(18f));
            case 2 -> Home_Label.setFont(current.deriveFont(24f));
            default -> { /* no change */ }
        }
    }

    private void openStudyHistory() {
        JOptionPane.showMessageDialog(
                this,
                "Study History feature coming soon!",
                "Study History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Entry point if this frame is run directly
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginView = new Login();
            LoginController controller = new LoginController(loginView);
            controller.open();
        });
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accountPopupMenu = new javax.swing.JPopupMenu();
        accountMenuItem = new javax.swing.JMenuItem();
        darkModeMenuItem = new javax.swing.JMenuItem();
        fontSizeMenuItem = new javax.swing.JMenuItem();
        studyHistoryMenuItem = new javax.swing.JMenuItem();
        logoutMenuItem = new javax.swing.JMenuItem();
        topPanel1 = new javax.swing.JPanel();
        Home_Button = new javax.swing.JButton();
        Logo_label = new javax.swing.JLabel();
        accountButton = new javax.swing.JButton();
        Library_Button = new javax.swing.JButton();
        centerpanel = new javax.swing.JPanel();
        Create_Button = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        QuickStart_label = new javax.swing.JTextField();
        Home_Label = new javax.swing.JLabel();

        accountPopupMenu.setBackground(new java.awt.Color(102, 102, 102));
        accountPopupMenu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(200, 200, 200), new java.awt.Color(150, 150, 150), new java.awt.Color(80, 80, 82), new java.awt.Color(100, 100, 100)));

        accountMenuItem.setText("Account");
        accountMenuItem.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(200, 200, 200), new java.awt.Color(150, 150, 150), new java.awt.Color(80, 80, 80), new java.awt.Color(100, 100, 100)));
        accountPopupMenu.add(accountMenuItem);

        darkModeMenuItem.setText("Dark mode/ Light mode");
        accountPopupMenu.add(darkModeMenuItem);

        fontSizeMenuItem.setText("Font size");
        accountPopupMenu.add(fontSizeMenuItem);

        studyHistoryMenuItem.setText("Study history");
        accountPopupMenu.add(studyHistoryMenuItem);

        logoutMenuItem.setText("Log out");
        accountPopupMenu.add(logoutMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(240, 240, 240));
        getContentPane().setLayout(null);

        topPanel1.setBackground(new java.awt.Color(255, 255, 255));
        topPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        topPanel1.setLayout(null);

        Home_Button.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        Home_Button.setText("Home");
        Home_Button.setBorder(null);
        Home_Button.addActionListener(this::Home_ButtonActionPerformed);
        topPanel1.add(Home_Button);
        Home_Button.setBounds(190, 20, 60, 20);

        Logo_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        topPanel1.add(Logo_label);
        Logo_label.setBounds(100, 10, 60, 50);

        accountButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Untitled design (40).png"))); // NOI18N
        accountButton.setBorder(null);
        accountButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountButtonMouseClicked(evt);
            }
        });
        accountButton.addActionListener(this::accountButtonActionPerformed);
        topPanel1.add(accountButton);
        accountButton.setBounds(1130, 10, 50, 50);

        Library_Button.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        Library_Button.setText("Library");
        Library_Button.setBorder(null);
        topPanel1.add(Library_Button);
        Library_Button.setBounds(280, 10, 70, 40);

        getContentPane().add(topPanel1);
        topPanel1.setBounds(0, 0, 1330, 70);

        centerpanel.setBackground(new java.awt.Color(255, 255, 255));
        centerpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        centerpanel.setLayout(null);

        Create_Button.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        Create_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit 17.png"))); // NOI18N
        Create_Button.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Create_Button.setLabel("      Create Cards");
        Create_Button.addActionListener(this::Create_ButtonActionPerformed);
        centerpanel.add(Create_Button);
        Create_Button.setBounds(80, 70, 930, 60);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        centerpanel.add(jPanel5);
        jPanel5.setBounds(120, 230, 0, 0);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        centerpanel.add(jPanel8);
        jPanel8.setBounds(310, 230, 0, 0);

        QuickStart_label.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        QuickStart_label.setForeground(new java.awt.Color(153, 153, 153));
        QuickStart_label.setText("QUICK START");
        QuickStart_label.setBorder(null);
        QuickStart_label.addActionListener(this::QuickStart_labelActionPerformed);
        centerpanel.add(QuickStart_label);
        QuickStart_label.setBounds(80, 30, 100, 30);

        getContentPane().add(centerpanel);
        centerpanel.setBounds(100, 150, 1080, 530);

        Home_Label.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Home_Label.setText("Home");
        getContentPane().add(Home_Label);
        Home_Label.setBounds(100, 90, 140, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Create_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Create_ButtonActionPerformed
    // Pass the current frame (Dashtwo) as parent
    NewDeckDialog dialog = new NewDeckDialog(Dashtwo.this, true);
    dialog.setLocationRelativeTo(Dashtwo.this); // Center on dashboard
    dialog.setVisible(true); // Show the popup

    }//GEN-LAST:event_Create_ButtonActionPerformed

    private void QuickStart_labelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuickStart_labelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QuickStart_labelActionPerformed

    private void accountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountButtonActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_accountButtonActionPerformed

    private void accountButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountButtonMouseClicked
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) { // Left click only
            int x = accountButton.getWidth() - accountPopupMenu.getPreferredSize().width;
            int y = accountButton.getHeight();
            accountPopupMenu.show(accountButton, x, y);
        }
    }//GEN-LAST:event_accountButtonMouseClicked

    private void Home_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Home_ButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_Home_ButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Create_Button;
    private javax.swing.JButton Home_Button;
    private javax.swing.JLabel Home_Label;
    private javax.swing.JButton Library_Button;
    private javax.swing.JLabel Logo_label;
    private javax.swing.JTextField QuickStart_label;
    private javax.swing.JButton accountButton;
    private javax.swing.JMenuItem accountMenuItem;
    private javax.swing.JPopupMenu accountPopupMenu;
    private javax.swing.JPanel centerpanel;
    private javax.swing.JMenuItem darkModeMenuItem;
    private javax.swing.JMenuItem fontSizeMenuItem;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JMenuItem studyHistoryMenuItem;
    private javax.swing.JPanel topPanel1;
    // End of variables declaration//GEN-END:variables

}

