/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

import view.archivedel;
import view.cardpanel;
import model.ArchiveModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.List;

public class ArchivedelController {
    private final archivedel archiveDelView;
    private final ArchiveService archiveService;
    private int currentUserId = 1;
    
    public ArchivedelController(archivedel archiveDelView) {
        this.archiveDelView = archiveDelView;
        this.archiveService = new ArchiveService();
        
        try {
            archiveService.setCurrentUserId(currentUserId);
            setupListeners();
            loadDeletedFiles();
            System.out.println("ArchivedelController setup complete");
        } catch (Exception e) {
            System.err.println("ERROR in ArchivedelController: " + e);
            e.printStackTrace();
        }
    }
    
    private void setupListeners() {
        // Add listeners for buttons (similar to ArchiveController)
        // You'll need to add getter methods to archivedel.java first
    }
    
    private void loadDeletedFiles() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        List<ArchiveModel> deletedFiles = archiveService.getDeletedFiles();
        
        if (deletedFiles.isEmpty()) {
            JLabel noItemsLabel = new JLabel("No deleted files found.");
            noItemsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            container.add(noItemsLabel);
        } else {
            for (ArchiveModel file : deletedFiles) {
                cardpanel card = new cardpanel();
                card.setFileName(file.getFileName());
                
                // Show only unarchivepanel, hide archivepanel
                card.getUnarchivepanel().setVisible(true);
                card.getArchivepanel().setVisible(false);
                
                // Set up restore button listener
                card.getRestorebtn().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleRestoreDeletedFile(file.getArchiveId(), card);
                    }
                });
                
                // Set up delete button listener
                card.getDeletebtn().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleDeletePermanently(file.getArchiveId(), card);
                    }
                });
                
                container.add(card);
            }
        }
        
        // You'll need to add a getter for jScrollPane2 in archivedel.java
        // archiveDelView.getJScrollPane2().setViewportView(container);
    }
    
    private void handleRestoreDeletedFile(int archiveId, cardpanel card) {
        int confirm = JOptionPane.showConfirmDialog(archiveDelView,
            "Restore this deleted file?",
            "Confirm Restore",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = archiveService.restoreFile(archiveId);
            if (success) {
                JOptionPane.showMessageDialog(archiveDelView, "File restored successfully!");
                loadDeletedFiles(); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(archiveDelView, "Failed to restore file!");
            }
        }
    }
    
    private void handleDeletePermanently(int archiveId, cardpanel card) {
        int confirm = JOptionPane.showConfirmDialog(archiveDelView,
            "Permanently delete this file? This action cannot be undone.",
            "Confirm Permanent Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = archiveService.deleteFilePermanently(archiveId);
            if (success) {
                JOptionPane.showMessageDialog(archiveDelView, "File permanently deleted!");
                loadDeletedFiles(); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(archiveDelView, "Failed to delete file!");
            }
        }
    }
    
    public void open() {
        archiveDelView.setVisible(true);
    }
}