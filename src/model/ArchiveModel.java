/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class ArchiveModel {
    private int archiveId;
    private String fileName;
    private String fileType;
    private String archivedDate;
    private String archivedBy;
    private String status; // "archived" or "deleted"
    
    public ArchiveModel(int archiveId, String fileName, String fileType, String archivedDate) {
        this.archiveId = archiveId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.archivedDate = archivedDate;
        this.archivedBy = "System";
        this.status = "archived";
    }
    
    public ArchiveModel(int archiveId, String fileName, String fileType, 
                       String archivedDate, String archivedBy, String status) {
        this.archiveId = archiveId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.archivedDate = archivedDate;
        this.archivedBy = archivedBy;
        this.status = status;
    }
    
    public void setArchiveId(int archiveId) {
        this.archiveId = archiveId;
    }
    
    public int getArchiveId() {
        return archiveId;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public String getFileType() {
        return fileType;
    }
    
    public void setArchivedDate(String archivedDate) {
        this.archivedDate = archivedDate;
    }
    
    public String getArchivedDate() {
        return archivedDate;
    }
    
    public void setArchivedBy(String archivedBy) {
        this.archivedBy = archivedBy;
    }
    
    public String getArchivedBy() {
        return archivedBy;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
}
