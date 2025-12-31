/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

import view.archive2;
import view.cardpanel;
import model.ArchiveModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.List;

public class ArchiveController {
    private final archive2 archiveView;
    private final ArchiveService archiveService;
    private int currentUserId = 1;
    
    public ArchiveController(archive2 archiveView) {
        this.archiveView = archiveView;
        this.archiveService = new ArchiveService();
        
        try {
            archiveService.setCurrentUserId(currentUserId);
            setupListeners();
            loadArchiveView();
            System.out.println("ArchiveController setup complete");
        } catch (Exception e) {
            System.err.println("ERROR in ArchiveController: " + e);
            e.printStackTrace();
        }
    }
    
    private void setupListeners() {
        // Archive button listener
        archiveView.getArchivebtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleArchiveButtonClick();
            }
        });
        
        // Edit button listener
        archiveView.getEditbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditButtonClick();
            }
        });
        
        // Home button listener
        archiveView.getHomeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleHomeButtonClick();
            }
        });
        
        // Library button listener
        archiveView.getLibraryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLibraryButtonClick();
            }
        });
        
        // Create button listener
        archiveView.getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateButtonClick();
            }
        });
    }
    
    private void loadArchiveView() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        List<ArchiveModel> archives = archiveService.getArchivedFiles();
        
        if (archives.isEmpty()) {
            JLabel noItemsLabel = new JLabel("No archived files found.");
            noItemsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            container.add(noItemsLabel);
        } else {
            for (ArchiveModel archive : archives) {
                cardpanel card = new cardpanel();
                card.setFileName(archive.getFileName());
                
                // Show only archivepanel, hide unarchivepanel
                card.getArchivepanel().setVisible(true);
                card.getUnarchivepanel().setVisible(false);
                
                // Set up unarchive button listener
                card.getUnarchivebtn1().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleUnarchiveFile(archive.getArchiveId(), card);
                    }
                });
                
                // Set up delete button listener
                card.getDeletebtn1().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleDeleteFile(archive.getArchiveId(), card);
                    }
                });
                
                container.add(card);
            }
        }
        
        archiveView.getJScrollPane1().setViewportView(container);
        archiveView.getJScrollPane1().revalidate();
        archiveView.getJScrollPane1().repaint();
    }
    
    private void handleArchiveButtonClick() {
        String fileName = JOptionPane.showInputDialog(archiveView, "Enter file name to archive:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            boolean confirmed = archiveService.showArchiveConfirmation(archiveView, fileName);
            if (confirmed) {
                JOptionPane.showMessageDialog(archiveView, "File archived successfully!");
                loadArchiveView(); // Refresh the list
            }
        }
    }
    
    private void handleUnarchiveFile(int archiveId, cardpanel card) {
        int confirm = JOptionPane.showConfirmDialog(archiveView,
            "Restore this file back to library?",
            "Confirm Restore",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = archiveService.restoreFile(archiveId);
            if (success) {
                JOptionPane.showMessageDialog(archiveView, "File restored successfully!");
                loadArchiveView(); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(archiveView, "Failed to restore file!");
            }
        }
    }
    
    private void handleDeleteFile(int archiveId, cardpanel card) {
        int confirm = JOptionPane.showConfirmDialog(archiveView,
            "Move this file to deleted archive?",
            "Confirm Move to Deleted",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = archiveService.moveToDeleted(archiveId);
            if (success) {
                JOptionPane.showMessageDialog(archiveView, "File moved to deleted archive!");
                loadArchiveView(); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(archiveView, "Failed to move file!");
            }
        }
    }
    
    private void handleEditButtonClick() {
        JOptionPane.showMessageDialog(archiveView, "Edit feature coming soon!");
    }
    
    private void handleHomeButtonClick() {
        JOptionPane.showMessageDialog(archiveView, "Home feature coming soon!");
    }
    
    private void handleLibraryButtonClick() {
        JOptionPane.showMessageDialog(archiveView, "Library feature coming soon!");
    }
    
    private void handleCreateButtonClick() {
        JOptionPane.showMessageDialog(archiveView, "Create feature coming soon!");
    }
    
    public void open() {
        archiveView.setVisible(true);
    }
}