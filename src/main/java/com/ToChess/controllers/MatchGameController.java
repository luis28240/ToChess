/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.models.game.ChessGame;
import com.ToChess.models.user.User;
import com.ToChess.services.chessGames.ChessGameService;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
@RequestMapping("chess-game")
public class MatchGameController {

    private final String GAME_TEST_ID = "TestGameId";

    private final ChessGameService gameService;

    @Autowired
    public MatchGameController(ChessGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{gameId}")
    public ModelAndView startPage(
            HttpSession session,
            @PathVariable("gameId") String gameId
    ) {
        if (session.getAttribute("user") == null) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView view = new ModelAndView("chess-game");

        User user = (User) session.getAttribute("user");
        ChessGame game = gameService.getChessGame(gameId);
        String type = game.getUserColor(user);
        if (type == null) {
            type = "spectator";
        }
        view.addObject("type", type);
        view.addObject("game", game);

        return view;
    }

}
