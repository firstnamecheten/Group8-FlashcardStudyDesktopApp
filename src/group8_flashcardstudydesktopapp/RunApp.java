/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group8_flashcardstudydesktopapp;
import view.FlipAndSwitch;
import controller.FlashcardController;

public class RunApp {
    public static void main(String[] args) {
        FlipAndSwitch view = new FlipAndSwitch();
        FlashcardController controller = new FlashcardController(view);
        controller.open();
    }
}