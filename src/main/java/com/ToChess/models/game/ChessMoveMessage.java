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
public class ChessMoveMessage extends ChessMove{
    
    private final String fen;
    private final String pgn;

    public ChessMoveMessage(String fen, String pgn, String from, String to, String promotion) {
        super(from, to, promotion);
        this.fen = fen;
        this.pgn = pgn.replace("\n", "");
    }

    public String getFen() {
        return fen;
    }

    public String getPgn() {
        return pgn;
    }
    
}
