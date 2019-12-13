/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.models.game.ChessGame;
import com.ToChess.models.user.User;
import com.ToChess.services.chessGames.ChessGameService;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Tarde
 */
@Component
public class WebSocketMatchPlayersHandler extends TextWebSocketHandler {

    private final LinkedList<WebSocketSession> sessionList;
    private final ChessGameService gameService;

    @Autowired
    public WebSocketMatchPlayersHandler(ChessGameService gameService) {
        sessionList = new LinkedList<>();
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        if (!sessionList.contains(session)) {
            sessionList.addLast(session);
            String requestType = message.getPayload();
            if (requestType.equals("match")) {
                if (sessionList.size() >= 2) {

                    WebSocketSession session1 = sessionList.removeFirst();
                    WebSocketSession session2 = sessionList.removeFirst();

                    User user1 = (User) session1.getAttributes().get("user");
                    User user2 = (User) session2.getAttributes().get("user");

                    //Crear una partida
                    ChessGame newGame = new ChessGame();
                    newGame.matchPlayer(user1, user2);

                    session1.sendMessage(new TextMessage(newGame.getId()));
                    session2.sendMessage(new TextMessage(newGame.getId()));

                    gameService.addChessGame(newGame);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (sessionList.contains(session)) {
            sessionList.remove(session);
        }
    }

}
