/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.models.game.ChessGame;
import com.ToChess.models.user.User;
import com.ToChess.services.chessGames.ChessGameService;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;
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

    private final LinkedHashMap<String, WebSocketSession> sessionList;
    private final ChessGameService gameService;

    private WebSocketSession getRandomSession() {
        Random generator = new Random();
        Object[] values = sessionList.values().toArray();
        return (WebSocketSession) values[generator.nextInt(values.length)];
    }

    @Autowired
    public WebSocketMatchPlayersHandler(ChessGameService gameService) {
        sessionList = new LinkedHashMap<>();
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

//        User sessionUser = getUser(session);
        if (!sessionList.containsKey(session.getId())) {
            sessionList.put(session.getId(), session);
            String requestType = message.getPayload();
            if (requestType.equals("match")) {
                if (sessionList.size() >= 2) {
                    WebSocketSession session1 = getRandomSession();
                    
                    //Como es random, puede que escoga otra vez la session1, asi
                    //que comprobamos si ambas sesiones son iguales, si es asi
                    //se busca al azar otra sesion hasta que sean distintas
                    WebSocketSession session2 = getRandomSession();
                    while (session1 == session2) {
                        session2 = getRandomSession();
                    }
                    User user1 = getUser(session1);
                    User user2 = getUser(session2);

                    if (!user1.equals(user2)) {
                        //Crear una partida
                        ChessGame newGame = new ChessGame();
                        newGame.matchPlayer(user1, user2);

                        session1.sendMessage(new TextMessage(newGame.getId()));
                        session2.sendMessage(new TextMessage(newGame.getId()));

                        sessionList.remove(session1.getId());
                        sessionList.remove(session2.getId());
                        
                        gameService.addChessGame(newGame);
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (sessionList.containsKey(session.getId())) {
            sessionList.remove(session.getId());
        }
    }

    private User getUser(WebSocketSession session) {
        return (User) session.getAttributes().get("user");
    }

}
