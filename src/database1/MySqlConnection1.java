/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


// Note make a folder named database and inside make files named Database and MySqlConnection and keep that folder in .gitignore so everyone has thier own datbase folder hidden and thier wont be merge conflict.  
// Copy this code and paste in your MySqlConnection file that is in /gitignore (remove 1 from database1 , Database1 and MySqlConnection1. 
package database1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class MySqlConnection1 implements Database1{                  

    @Override
    public Connection openConnection() {
try{

            String username = "what is ur username of mysql workbench";              //team add their own username

            String password = "what is ur rootpasswor of mydql connection";             //team add their own passowrd

            String database = "database_connection";

            Connection connection;

            connection = DriverManager.getConnection(

                    "jdbc:mysql://localhost: /" + database, username, password          // add your localhost bumber after localhost:

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
