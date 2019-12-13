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
public class NoGameFoundException extends Exception{

    @Override
    public String toString() {
        return "No Game Found, try later";
    }
    
}
