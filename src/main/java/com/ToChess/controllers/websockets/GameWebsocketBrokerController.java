package com.ToChess.controllers.websockets;

import com.ToChess.models.game.ChessGame;
import com.ToChess.models.game.ChessMove;
import com.ToChess.models.game.ChessMoveMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;

import com.ToChess.services.chessGames.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luis
 */
@Controller
public class GameWebsocketBrokerController {

    private final ChessGameService gameService;

    @Autowired
    public GameWebsocketBrokerController(ChessGameService gameService) {
        this.gameService = gameService;
    }
    
    @MessageMapping("/game/play/{gameId}")
    public ChessGame play(
            ChessMoveMessage gameMessage,
            @DestinationVariable("gameId") String gameId
    ) {
        ChessGame currentGame = gameService.getChessGame(gameId);
        currentGame.setFen(gameMessage.getFen());
        currentGame.setPgn(gameMessage.getPgn());
        currentGame.addMove(gameMessage);
        return currentGame;
    }

}
