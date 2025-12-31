/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;

public class ArchiveDialog extends JDialog {
    private boolean confirmed = false;
    
    public ArchiveDialog(JFrame parent, String fileName) {
        super(parent, "Archive this file?", true);
        initComponents(fileName);
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents(String fileName) {
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel message = new JLabel("<html><b>Archive \"" + fileName + "\"?</b><br><br>" +
                                   "You can restore the file from the archive later.</html>");
        messagePanel.add(message, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JButton cancelBtn = new JButton("Cancel");
        JButton archiveBtn = new JButton("Archive");
        
        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        archiveBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(archiveBtn);
        
        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(350, 200);
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
}