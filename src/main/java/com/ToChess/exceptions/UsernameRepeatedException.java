/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.exceptions;

/**
 *
 * @author Tarde
 */
public class UsernameRepeatedException extends Exception{
    
    private final String username;

    public UsernameRepeatedException(String username) {
        super(String.format("user %s already exists", username));
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("user %s already exists", username);
    }
    
}
