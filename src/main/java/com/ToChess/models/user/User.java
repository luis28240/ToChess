/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.models.user;

import com.ToChess.models.game.ChessGame;
import java.util.HashMap;

/**
 *
 * @author Tarde
 */
public class User {
    
    public static final int USERNAME_LENGHT = 10;
    
    private final String username;
    private final HashMap<String, ChessGame> gameList;
    private String password;
    private String email;
    
    public User(String username) {
        this.username = username;
        gameList = new HashMap<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void addChessGame(ChessGame game){
        gameList.put(game.getId(), game);
    }
    
    public ChessGame getChessGame(String gameId){
        return gameList.get(gameId);
    }
    
    public void removeChessGame(String gameId){
        gameList.remove(gameId);
    }
    
}
