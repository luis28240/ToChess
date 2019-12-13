/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.models.game;

import com.ToChess.models.user.User;
import com.ToChess.util.RandomAlphanumeric;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tarde
 */
public class ChessGame {
    
    private final int ID_LENGHT = 10;
    
    private String fen;
    private String pgn;
    
    private final List<ChessMove> moveList;
    
    private final String id;
    private User blackPlayer;
    private User whitePlayer;

    public ChessGame() {
        RandomAlphanumeric random = new RandomAlphanumeric();
        moveList = new ArrayList<>();
        this.id = random.getRandom(ID_LENGHT);
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    }

    public void addMove(ChessMove move){
        moveList.add(move);
    }
    
    public ChessMove getLastMove(){
        return !moveList.isEmpty() ? moveList.get(moveList.size()-1) : null;
    }
    
    public ChessMove getFirstMove(){
        return !moveList.isEmpty() ? moveList.get(0) : null;
    }
    
    public ChessMove getNthMove(int nth){
        return !moveList.isEmpty() ? moveList.get(nth) : null;
    }
    
    public String getPgn() {
        return pgn;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }
    
    public String getId() {
        return id;
    }

    public String getUserColor(User user){
        if(blackPlayer == user){
            return "black";
        }else if(whitePlayer == user){
            return "white";
        }
        return null;
    }
    
    public void matchPlayer(User user1, User user2){
        
        double random = Math.random();
        if(random > 0.5){
            whitePlayer = user1;
            blackPlayer = user2;
        }else{
            whitePlayer = user2;
            blackPlayer = user1;
        }
        user1.addChessGame(this);
        user2.addChessGame(this);
    }
    
    public User getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(User blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public User getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(User whitePlayer) {
        this.whitePlayer = whitePlayer;
    }
    
    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.ID_LENGHT;
        hash = 47 * hash + Objects.hashCode(this.fen);
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.blackPlayer);
        hash = 47 * hash + Objects.hashCode(this.whitePlayer);
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
        final ChessGame other = (ChessGame) obj;
        if (this.ID_LENGHT != other.ID_LENGHT) {
            return false;
        }
        if (!Objects.equals(this.fen, other.fen)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.blackPlayer, other.blackPlayer)) {
            return false;
        }
        return Objects.equals(this.whitePlayer, other.whitePlayer);
    }
    
 }
