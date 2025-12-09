/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.Login;

public class LoginController {
    private final Login login;
    public LoginController(Login login) {
        this.login = login;
    }


    public void open(){
        this.login.setVisible(true);
    }
}

