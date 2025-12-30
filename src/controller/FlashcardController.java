/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.FlipAndSwitch;
import view.CardsPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class FlashcardController {
    private final FlipAndSwitch view;
    private final FlashcardService service;
    
    public FlashcardController(FlipAndSwitch view) {
        this.view = view;
        this.service = new FlashcardService();
        setupListeners();
        updateView();
    }
    
    private void setupListeners() {
        // Card panel flip button
        view.getCardsPanelComponent().addFlipButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipCard();
            }
        });
        
        // Navigation buttons
        view.addPrevButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousCard();
            }
        });
        
        view.addNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextCard();
            }
        });
    }
    
    private void updateView() {
        // Update card content
        String cardText = service.getCurrentCardText();
        boolean isAnswer = service.getCurrentSide().equals("Answer");
        
        CardsPanel cardPanel = view.getCardsPanelComponent();
        cardPanel.setCardText(cardText);
        cardPanel.setSideIndicator(service.getCurrentSide());
        
        // Update status labels
        view.setCardCount(service.getCurrentCardNumber() + "/" + service.getTotalCards());
        view.setScore(service.calculateScore() + "%");
        view.setCategory(service.getCurrentCategory());
        view.setDifficulty(service.getCurrentDifficulty());
    }
    
    private void flipCard() {
        service.flipCurrentCard();
        updateView();
    }
    
    private void previousCard() {
        if (service.previousCard()) {
            updateView();
        } else {
            JOptionPane.showMessageDialog(view, "This is the first card!");
        }
    }
    
    private void nextCard() {
        if (service.nextCard()) {
            service.markCurrentAsReviewed();
            updateView();
        } else {
            JOptionPane.showMessageDialog(view, "This is the last card!");
        }
    }
    
    public void open() {
        view.setVisible(true);
    }
}