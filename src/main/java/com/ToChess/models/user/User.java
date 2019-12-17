/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.models.user;

import com.ToChess.models.game.ChessGame;
import java.util.HashMap;
import java.util.Objects;

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
    private boolean loggedUser;

    public User(String username) {
        this.username = username;
        gameList = new HashMap<>();
        loggedUser = false;
    }

    public boolean isLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(boolean loggedUser) {
        this.loggedUser = loggedUser;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.username);
        hash = 23 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if(other.getUsername().equals("anonymous") && username.equals("anonymous")){
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.email, other.email);
    }


    
}
