/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group8_flashcardstudydesktopapp;

import controller.UserController;
import view.Login;
import view.Signup;

/**
 *
 * @author LENOVO
 */
public class Group8_FlashcardStudyDesktopApp {

    private static Login loginView;
    
    public static void main(String[] args){
        Signup signupView = new Signup();
        UserController uc = new UserController(signupView, loginView);   // aba open gara signup page bhaneko
        uc.open();
    }
}