/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD
=======
package group8_flashcardstudydesktopapp;

import controller.UserController;

import view.Signup;

/**
 *
 * @author LENOVO
 */
public class Group8_FlashcardStudyDesktopApp {  
    
    public static void main(String[] args){
        Signup signupView = new Signup();
        UserController uc = new UserController(signupView);   // aba open gara signup page bhaneko
        uc.open();
    }
}
>>>>>>> 8b81028a883609b0d62c98b8ac60ee61e2703396
