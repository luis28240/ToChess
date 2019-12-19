/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.exceptions.NoGameFoundException;
import com.ToChess.models.game.ChessGame;
import com.ToChess.models.user.User;
import com.ToChess.services.chessGames.ChessGameService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
@RequestMapping("/")
public class HomepageController {

    private final String GAME_TEST_ID = "TestGameId";

    @Autowired
    private ChessGameService gameService;

    @RequestMapping
    public ModelAndView chessGameStart(HttpSession session) {
        ModelAndView view = new ModelAndView("index");

        Object userObj = session.getAttribute("user");
        if (userObj == null) {
//            String username = "user-" + session.getId().substring(0, User.USERNAME_LENGHT);
            String username = "anonymous";
            User user = new User(username);
            session.setAttribute("user", user);
        }

        return view;
    }

    @RequestMapping("buscarPartida")
    @ResponseBody
    public String buscarPartida(HttpSession session) throws NoGameFoundException {

        Object userObj = session.getAttribute("user");
        if (userObj != null) {
            User user = (User) session.getAttribute("user");

            gameService.addPlayerToQueue(user);
            boolean isGame = gameService.isGameAvailable();
            if (isGame ) {
                System.out.println("Hay juegos disponibles");
                ChessGame newGame = gameService.matchPlayers();
                return newGame.getId();
            } else {
                System.out.println("No hay juegos disponibles");
            }
        }

        throw new NoGameFoundException();
    }

}
