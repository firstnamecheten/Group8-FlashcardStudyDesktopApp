/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author LENOVO
 */
public class AdminLoginController {
    // Simple hardcoded check (later you can connect to DB)
    public boolean checkLogin(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }
}
