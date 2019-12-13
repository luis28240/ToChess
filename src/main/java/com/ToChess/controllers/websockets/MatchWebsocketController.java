package com.ToChess.controllers.websockets;

import com.ToChess.models.game.ChessGame;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static com.ToChess.configurations.WebSocketBrokerConfig.*;

/**
 *
 * @author Luis
 */
@Controller
public class MatchWebsocketController {
    
    @MessageMapping("/findMatch")
    @SendTo(TOPIC_GAMES+"/matchFinded/update")
    public ChessGame play(ChessGame game){
        return game;
    }
    
}
