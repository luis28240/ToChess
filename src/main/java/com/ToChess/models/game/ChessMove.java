/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.models.game;

/**
 *
 * @author Tarde
 */
public class ChessMove {
    
    protected final String from;
    protected final String to;
    protected final String promotion;

    public ChessMove(String from, String to, String promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
    
    public String getPromotion() {
        return promotion;
    }
    
}
