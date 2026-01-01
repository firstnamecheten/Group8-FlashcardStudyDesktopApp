/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;

/**
 *
 * @author samee
 */
public class Mysqlconnection implements Database {

    @Override
    public Connection openConnection() {
       try{
<<<<<<< HEAD

            String username = "root";

<<<<<<< HEAD
            String password = "root password";
=======
            String password = "rootpassword";
>>>>>>> main

            String database = "database_connection";

            Connection connection;

            connection = DriverManager.getConnection(

                    "jdbc:mysql://localhost:3306/" + database, username, password

            );

            if(connection == null){

                System.out.println("Database connection fail");

            }else{

                System.out.println("Database connection success");

            }

            return connection;

        }catch(Exception e){

            System.out.println(e);

            return null;

        }

    }

    @Override

    public void closeConnection(Connection conn) {

        try{

            if(conn != null && !conn.isClosed() ){

                conn.close();

                System.out.println("Connection close");

            }

        }catch(Exception e){

            System.out.println(e);      

        }

    }

    @Override

    public ResultSet runQuery(Connection conn, String query) {

       try{

           Statement stmp = conn.createStatement();

           ResultSet result = stmp.executeQuery(query);

           return result;
           

       }catch (Exception e){

=======
           String username = "root";
           String password = "asdfghjkl;'";
           String database = "project";
           Connection connection;
           connection = DriverManager.getConnection(
           "jdbc:mysql://localhost:3306/" +database,username,password);
           if(connection == null){
               System.out.println("Connection failed");
           }else{
               System.out.println("Connection sucessful");
           }
           return connection;
       }catch(Exception e){
>>>>>>> origin/main
           System.out.println(e);
           return null;
       }
    }

    @Override
    public void closeConnection(Connection conn) {
        try{
            if(conn!=null && !conn.isClosed()){
                conn.close();
                System.out.println("connection close");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try{
            Statement stmp = conn.createStatement();
           ResultSet result = stmp.executeQuery(query);
           return result;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        
        
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
        try{
            Statement stmp = conn.createStatement();
          int result = stmp.executeUpdate(query);
          return result;
        }catch(Exception e){
            System.out.println(e);
            return -1;
            
        }
        
    }
    
    
}