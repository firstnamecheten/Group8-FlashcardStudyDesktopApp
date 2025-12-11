/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author LENOVO
 */
public class MySqlConnection implements Database {
    
    @Override
    public Connection openConnection() {               //this opens connection
        try {
            String username = "root";           // your MySQL username
            String password = "rootpassword";           // your MySQL password
            String database = "database_connection";        // your actual database name
            Connection connection;
            connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + database, username, password);
            if (connection == null)
            {
                System.out.println("Connection Unsuccessful");
            } 
            else
            {
                System.out.println("Connection successful");
            }
            return connection;
            } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void closeConnection(Connection conn) {          //this opens connection
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection close");
            }
            }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {         //this select ko case ma select gardineh bhayo
        try {
            Statement stmp = conn.createStatement();
            ResultSet result = stmp.executeQuery(query);
            return result;
        } 
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {             //yo chai update ko casema
        try {
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

}
