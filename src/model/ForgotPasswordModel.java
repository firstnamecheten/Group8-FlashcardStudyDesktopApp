/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class ForgotPasswordModel {
    private String username;              // from MySQL
    private String password;

    // Constructor for LOGIN / DB (with user_id + password)
     public ForgotPasswordModel (String username, String password) {
        this.username = username;
        this.password = password;
}
     
     // GETTERS & SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // Make sure this actually sets password
    public void setPassword(String password) {
        this.password = password;
    }
}

