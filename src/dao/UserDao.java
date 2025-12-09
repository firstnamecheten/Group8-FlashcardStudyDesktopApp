/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.MySqlConnection;
import model.UserModel;
import java.sql.*;


public class UserDao {                                             //userdao jasma query lekcha --> sql ko query insert garnu ko lagi signup method banako cha yaha, yeh leh communication garcha 
    MySqlConnection mysql = new MySqlConnection();               
    
    public void signUp(UserModel user){                         // yo method leh jun user bata ako data lai store gardincha
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"; 
        try(PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, user.getUsername());     //this is used to add field eg for flascard we have
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.executeUpdate();
        }catch(Exception ex){
            System.out.println("Error inserting user: " + ex);
        } finally{
            mysql.closeConnection(conn);
        }
        
    }
    public boolean check(UserModel user){                     //yehleh check garcha yo user cha ki nai duplicate user cha ki nai hercha 
        Connection conn = mysql.openConnection();
        String sql = "select * from users where email = ? or username = ?";
        try(PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, user.getEmail());     //this is used to add field eg for flascard we have
            pstm.setString(2, user.getUsername());
            ResultSet result = pstm.executeQuery();
            return result.next();
        }catch(Exception ex){
            System.out.println(ex);
        } finally{
            mysql.closeConnection(conn);
        }
        return false;
    }
}
// this was made to check username and email. Check garcha jaba samma rows sakidaina taba samma. Yedi same cha bhane tya value store garirako huncha. Yo method call garcha. Jaba yo method call huncha kunchai method call huneh bhayo?--> check method!