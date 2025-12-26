package view;

import controller.LoginController;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import model.UserModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class Dashtwo extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Dashtwo.class.getName());

    // Track the currently active dashboard
    private static Dashtwo ACTIVE_DASHBOARD;
    private boolean isDarkMode;
    private final UserModel currentUser;
    // Keep track of the current logged-in user
    private JButton createDeckButton;

    private JButton createCardsButton; // grey button shown in image
    private int createdDeckId = -1;
    public int getCreatedDeckId() { return createdDeckId; }

    private JButton createButton;
    private java.util.List<JButton> deckButtons = new ArrayList<>();

    // Track if user has any decks
    private boolean hasDecks = false;

    // Panel that holds deck buttons vertically
    private JPanel deckContainer;

    // Controls whether we auto-open CreateFlashcards after creating a deck
    private boolean openFlashcardsOnCreate = false; // default: stay on Dashtwo

    // === Constructors ===
    /** Creates new form Dashtwo (preferred: with user context) */
    public Dashtwo(UserModel user) {
        initComponents();

        // Style the Create button before layout
        CreateButton.setPreferredSize(new Dimension(160, 40));
        CreateButton.setFont(new Font("Dialog", Font.BOLD, 15));
        CreateButton.setFocusPainted(false);

        initDeckContainer();
        // IMPORTANT: keep Create visible always
        CreateButton.setVisible(true);

        setSize(1285, 760);
        this.currentUser = user;
        ACTIVE_DASHBOARD = this; // track this dashboard
        wireMenuActions();

        // Load saved decks so they persist
        loadDecksFromStorage();
    }

    /** No-arg constructor (needed for GUI builder or fallback) */
    public Dashtwo() {
        initComponents();

        CreateButton.setPreferredSize(new Dimension(160, 40));
        CreateButton.setFont(new Font("Dialog", Font.BOLD, 15));
        CreateButton.setFocusPainted(false);

        initDeckContainer();
        // IMPORTANT: keep Create visible always
        CreateButton.setVisible(true);

        setSize(1285, 760);
        this.currentUser = null; // fallback
        ACTIVE_DASHBOARD = this; // track this dashboard
        wireMenuActions();

        // Load saved decks so they persist
        loadDecksFromStorage();
    }

    // Getter for the active dashboard
    public static Dashtwo getActiveDashboard() { return ACTIVE_DASHBOARD; }

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
        this.setVisible(false); // hide dashboard while account page is open
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
            scrollPane1.setBackground(new java.awt.Color(45, 45, 45));

            Home_Button.setForeground(new java.awt.Color(200, 200, 200));
            Home_Button.setBackground(new java.awt.Color(45, 45, 45)); // dark background
            Home_Button.setOpaque(true);
            Library_Button.setForeground(new java.awt.Color(200, 200, 200));
            Library_Button.setBackground(new java.awt.Color(45, 45, 45));
            Library_Button.setOpaque(true);

            Home_Label.setForeground(new java.awt.Color(255, 255, 255));
            Home_Label.setBackground(new java.awt.Color(45, 45, 45));
            Logo_label.setBackground(new java.awt.Color(45, 45, 45));
            accountButton.setForeground(new java.awt.Color(255, 255, 255));
            accountButton.setBackground(new java.awt.Color(40, 40, 40));

            darkModeMenuItem.setText(" Light mode");
        } else {
            getContentPane().setBackground(new java.awt.Color(240, 240, 240));
            topPanel1.setBackground(new java.awt.Color(255, 255, 255));
            scrollPane1.setBackground(new java.awt.Color(255, 255, 255));

            Home_Button.setForeground(new java.awt.Color(0, 0, 0));
            Home_Button.setBackground(new java.awt.Color(254, 254, 254));
            Home_Button.setOpaque(true);
            Library_Button.setForeground(new java.awt.Color(0, 0, 0));
            Library_Button.setBackground(new java.awt.Color(254, 254, 254));
            Library_Button.setOpaque(true);
            Home_Label.setForeground(new java.awt.Color(0, 0, 0));
            Home_Label.setBackground(new java.awt.Color(240, 240, 240));
            accountButton.setForeground(new java.awt.Color(0, 0, 0));
            accountButton.setBackground(new java.awt.Color(254, 254, 254));
            accountButton.setOpaque(true);

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
                "Study History feature coming soon will be done by Bipin!",
                "Study History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
        // === Create button clicks ===

    
    private void openNewDeckDialog() {
        NewDeckDialog dialog = new NewDeckDialog(this, true, currentUser);
        dialog.setLocationRelativeTo(this); // center on Dashtwo frame
        dialog.setVisible(true);
    
        }
    
    
    private void openStudycards2() {
        
    }
    
    
    
    // === Deck area setup using scrollPane1 ===
    private void initDeckContainer() {
        deckContainer = new JPanel();
        deckContainer.setLayout(new BoxLayout(deckContainer, BoxLayout.Y_AXIS));

        // Attach deck container to NetBeans scrollPane1
        // Note: scrollPane1 is a Swing JScrollPane in this class
        scrollPane1.setViewportView(deckContainer);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
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
        Library_Button = new javax.swing.JButton();
        accountButton = new javax.swing.JButton();
        scrollbar2 = new java.awt.Scrollbar();
        Home_Label = new javax.swing.JLabel();
        CreateButton = new javax.swing.JButton();
        DeckOrganizationButton = new javax.swing.JButton();
        scrollPane1 = new javax.swing.JScrollPane();

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

        Home_Button.setBackground(new java.awt.Color(254, 254, 254));
        Home_Button.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        Home_Button.setText("Home");
        Home_Button.setBorder(null);
        Home_Button.addActionListener(this::Home_ButtonActionPerformed);

        Logo_label.setBackground(new java.awt.Color(254, 254, 254));
        Logo_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/122.png"))); // NOI18N

        Library_Button.setBackground(new java.awt.Color(254, 254, 254));
        Library_Button.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        Library_Button.setText("Library");
        Library_Button.setBorder(null);

        accountButton.setBackground(new java.awt.Color(254, 254, 254));
        accountButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/13_1.png"))); // NOI18N
        accountButton.setBorder(null);
        accountButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountButtonMouseClicked(evt);
            }
        });
        accountButton.addActionListener(this::accountButtonActionPerformed);

        javax.swing.GroupLayout topPanel1Layout = new javax.swing.GroupLayout(topPanel1);
        topPanel1.setLayout(topPanel1Layout);
        topPanel1Layout.setHorizontalGroup(
            topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanel1Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(Logo_label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Home_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(Library_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 774, Short.MAX_VALUE)
                .addComponent(accountButton)
                .addGap(144, 144, 144))
        );
        topPanel1Layout.setVerticalGroup(
            topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanel1Layout.createSequentialGroup()
                .addGroup(topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logo_label)
                    .addGroup(topPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(Home_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(topPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(Library_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(accountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(topPanel1);
        topPanel1.setBounds(0, 0, 1330, 70);

        Home_Label.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Home_Label.setText("Home");
        getContentPane().add(Home_Label);
        Home_Label.setBounds(100, 90, 140, 40);

        CreateButton.setBackground(new java.awt.Color(0, 153, 255));
        CreateButton.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        CreateButton.setForeground(new java.awt.Color(255, 255, 255));
        CreateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/create.png"))); // NOI18N
        CreateButton.setText("  Create ");
        CreateButton.setBorder(null);
        CreateButton.addActionListener(this::CreateButtonActionPerformed);
        getContentPane().add(CreateButton);
        CreateButton.setBounds(1060, 110, 120, 30);

        DeckOrganizationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Screenshot 2025-12-17 205936.png"))); // NOI18N
        DeckOrganizationButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        DeckOrganizationButton.addActionListener(this::DeckOrganizationButtonActionPerformed);
        getContentPane().add(DeckOrganizationButton);
        DeckOrganizationButton.setBounds(1020, 110, 30, 30);

        scrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        getContentPane().add(scrollPane1);
        scrollPane1.setBounds(100, 150, 1080, 530);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        // TODO add your handling code here:
     // ✅ Just call the helper method
    openNewDeckDialog();


    }//GEN-LAST:event_CreateButtonActionPerformed

    private void DeckOrganizationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeckOrganizationButtonActionPerformed
        // TODO add your handling code here:
      



    }//GEN-LAST:event_DeckOrganizationButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreateButton;
    private javax.swing.JButton DeckOrganizationButton;
    private javax.swing.JButton Home_Button;
    private javax.swing.JLabel Home_Label;
    private javax.swing.JButton Library_Button;
    private javax.swing.JLabel Logo_label;
    private javax.swing.JButton accountButton;
    private javax.swing.JMenuItem accountMenuItem;
    private javax.swing.JPopupMenu accountPopupMenu;
    private javax.swing.JMenuItem darkModeMenuItem;
    private javax.swing.JMenuItem fontSizeMenuItem;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JScrollPane scrollPane1;
    private java.awt.Scrollbar scrollbar2;
    private javax.swing.JMenuItem studyHistoryMenuItem;
    private javax.swing.JPanel topPanel1;
    // End of variables declaration//GEN-END:variables

    
    void addDeckButton(String deckName, int deckId) {
    JButton deckButton = new JButton(deckName);
    deckButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    deckButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
    deckButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
    deckButton.setFocusPainted(false);

    deckButton.addActionListener(e -> {
    Studycards2 studyPage = new Studycards2(deckId, deckName, currentUser);
    studyPage.setVisible(true);
    });

    deckContainer.add(deckButton);
    deckContainer.add(Box.createVerticalStrut(10));
    deckButtons.add(deckButton);

    deckContainer.revalidate();
    deckContainer.repaint();

    }
    
    // Example: store decks in a simple text file decks.txt
void saveDeckToStorage(int deckId, String deckName) {
    try (FileWriter fw = new FileWriter("decks.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            // Save compact, clean CSV
            out.println(deckId + "," + deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private void loadDecksFromStorage() {
    File file = new File("decks.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    int deckId = Integer.parseInt(parts[0].trim());
                    String deckName = parts[1].trim();
                    addDeckButton(deckName, deckId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




