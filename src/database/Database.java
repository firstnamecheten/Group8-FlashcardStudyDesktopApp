/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.*;

/**
 *
 * @author LENOVO
 */
public interface Database {
    Connection openConnection();                                 // this  opens connection
    void closeConnection(Connection conn);                       // this closes connection
    ResultSet runQuery(Connection conn, String query);           //this select ko case ma select gardineh bhayo 
    int executeUpdate(Connection conn, String query);            //yo chai update ko casema

}

