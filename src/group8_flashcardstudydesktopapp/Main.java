/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group8_flashcardstudydesktopapp;

import view.archive2;
import controller.ArchiveController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            archive2 archiveView = new archive2();
            ArchiveController controller = new ArchiveController(archiveView);
            controller.open();
        });
    }
}