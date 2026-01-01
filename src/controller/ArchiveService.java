/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ArchiveDAO;
import view.ArchiveDialog;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class ArchiveService {
    private final ArchiveDAO archiveDao;
    private int currentUserId;
    
    public ArchiveService() {
        this.archiveDao = new ArchiveDAO();
    }
    
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
        archiveDao.setUserId(userId);
    }
    
    // CHANGE: Frame → JFrame
    public boolean showArchiveConfirmation(javax.swing.JFrame parent, String fileName) {
        ArchiveDialog dialog = new ArchiveDialog(parent, fileName);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            return archiveFile(fileName, "Document");
        }
        return false;
    }
    
    public boolean archiveFile(String fileName, String fileType) {
        try {
            boolean success = archiveDao.archiveFile(fileName, fileType);
            if (success) {
                System.out.println("File archived: " + fileName);
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Error archiving file: " + e.getMessage());
            return false;
        }
    }
    
    public List<model.ArchiveModel> getArchivedFiles() {
        return archiveDao.getArchivedFiles();
    }
    
    public List<model.ArchiveModel> getDeletedFiles() {
        return archiveDao.getDeletedFiles();
    }
    
    public boolean restoreFile(int archiveId) {
        try {
            boolean success = archiveDao.restoreFile(archiveId);
            if (success) {
                System.out.println("File restored: " + archiveId);
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Error restoring file: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteFilePermanently(int archiveId) {
        try {
            boolean success = archiveDao.deleteFilePermanently(archiveId);
            if (success) {
                System.out.println("File permanently deleted: " + archiveId);
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }
    
    public boolean moveToDeleted(int archiveId) {
        try {
            boolean success = archiveDao.moveToDeleted(archiveId);
            if (success) {
                System.out.println("File moved to deleted: " + archiveId);
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Error moving file to deleted: " + e.getMessage());
            return false;
        }
    }
}