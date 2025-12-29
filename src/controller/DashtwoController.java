/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.Dashtwo;

/**
 *
 * @author LENOVO
 */
public class DashtwoController {
    private final Dashtwo dash;
    public DashtwoController(Dashtwo dash){
        this.dash = dash;
    }
    
    public void open(){
        this.dash.setVisible(true);
    }
    public void close (){
        this.dash.dispose();
    }
}
