/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.Dashboard;

/**
 *
 * @author LENOVO
 */
public class DashtwoController {
    private final Dashboard view;
    public DashtwoController(Dashboard view){
        this.view = view;
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    public void close (){
        this.view.dispose();
    }
}
