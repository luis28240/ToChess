/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.util;

/**
 *
 * @author Tarde
 */
public class RandomAlphanumeric {
 
    private final String ALPHANUMERICS;

    public RandomAlphanumeric() {
        this.ALPHANUMERICS = "QWEASDZXCRTYFGHVBNUIOPJKLNMqpwoeirutyalskdjfhgmznxbcv0487591326";
    }
    
    public String getRandom(int lenght){
        
        StringBuilder builder = new StringBuilder();

        while(lenght-- != 0){
            int characterPoint = (int)(Math.random()*ALPHANUMERICS.length());
            builder.append(ALPHANUMERICS.charAt(characterPoint));
        }
        
        return builder.toString();
    }
    
}
