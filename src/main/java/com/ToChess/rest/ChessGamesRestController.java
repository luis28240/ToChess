/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.rest;

import com.ToChess.models.game.ChessGame;
import com.ToChess.services.chessGames.ChessGameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tarde
 */
@RestController
@RequestMapping("/games")
public class ChessGamesRestController {
    
    private final ChessGameService gameService;

    @Autowired
    public ChessGamesRestController(ChessGameService gameService) {
        this.gameService = gameService;
    }
    
    @GetMapping
    public List<ChessGame> getAllGames(){
        return gameService.getGameList();
    }
    
}
