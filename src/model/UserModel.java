/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class UserModel {
    private int user_id;
    private String username;
    private String password;
    private String email;
    
    
    public UserModel(String username, String password, String email){
            this.username = username;
            this.password = password;
            this.email = email;
            
    }
    public void setUsername(String username){              //we are doing get set here so user leh data haru cannot access data
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    
    public void setId(int user_id){
        this.user_id = user_id;
    }
    public void setPassword(String password){
        this.username = password;
    }
    public String getPassword(){
        return password;
    }
     public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    
}
