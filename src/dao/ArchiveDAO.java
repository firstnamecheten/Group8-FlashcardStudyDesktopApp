/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import model.ArchiveModel;
import database.Mysqlconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDAO {
    Mysqlconnection mysql = new Mysqlconnection();
    private int userId;
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public boolean archiveFile(String fileName, String fileType) throws SQLException {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO archive_files (file_name, file_type, archived_date, user_id, status) " +
                    "VALUES (?, ?, NOW(), ?, 'archived')";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.setString(2, fileType);
            pstmt.setInt(3, userId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error archiving file: " + e);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    public List<ArchiveModel> getArchivedFiles() {
        List<ArchiveModel> archives = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM archive_files WHERE user_id = ? AND status = 'archived' ORDER BY archived_date DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ArchiveModel archive = new ArchiveModel(
                    rs.getInt("archive_id"),
                    rs.getString("file_name"),
                    rs.getString("file_type"),
                    rs.getString("archived_date"),
                    rs.getString("archived_by"),
                    rs.getString("status")
                );
                archives.add(archive);
            }
        } catch (Exception e) {
            System.out.println("Error fetching archived files: " + e);
        } finally {
            mysql.closeConnection(conn);
        }
        return archives;
    }
    
    public List<ArchiveModel> getDeletedFiles() {
        List<ArchiveModel> archives = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM archive_files WHERE user_id = ? AND status = 'deleted' ORDER BY archived_date DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ArchiveModel archive = new ArchiveModel(
                    rs.getInt("archive_id"),
                    rs.getString("file_name"),
                    rs.getString("file_type"),
                    rs.getString("archived_date"),
                    rs.getString("archived_by"),
                    rs.getString("status")
                );
                archives.add(archive);
            }
        } catch (Exception e) {
            System.out.println("Error fetching deleted files: " + e);
        } finally {
            mysql.closeConnection(conn);
        }
        return archives;
    }
    
    public boolean restoreFile(int archiveId) throws SQLException {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE archive_files SET status = 'restored' WHERE archive_id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, archiveId);
            pstmt.setInt(2, userId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error restoring file: " + e);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    public boolean deleteFilePermanently(int archiveId) throws SQLException {
        Connection conn = mysql.openConnection();
        String sql = "DELETE FROM archive_files WHERE archive_id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, archiveId);
            pstmt.setInt(2, userId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            System.out.println("Error deleting file permanently: " + e);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    public boolean moveToDeleted(int archiveId) throws SQLException {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE archive_files SET status = 'deleted' WHERE archive_id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, archiveId);
            pstmt.setInt(2, userId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error moving to deleted: " + e);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}