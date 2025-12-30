/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group8_flashcardstudydesktopapp;

import controller.UserController;
import database.Database;
import database.MySqlConnection;

import view.Signup;

/**
 *
 * @author LENOVO
 */
public class Group8_FlashcardStudyDesktopApp {

    
    public static void main(String[] args){
    
        Signup userView = new Signup();
        UserController uc = new UserController(userView);   // aba open gara signup page bhaneko
        uc.open();
    }
}