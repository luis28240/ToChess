/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.services.chessGames;

import com.ToChess.models.game.ChessGame;
import com.ToChess.models.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tarde
 */
@Service
public class ChessGameService {

    private final HashMap<String, ChessGame> chessGameList;
    private final Queue<User> waitingQueue;

    public ChessGameService() {
        this.chessGameList = new HashMap<>();
        this.waitingQueue = new LinkedList<>();
    }

    public void addPlayerToQueue(User player) {
        if (!waitingQueue.contains(player)) {
            waitingQueue.add(player);
        }
    }

    public List<ChessGame> getGameList(){
        return new ArrayList<>(chessGameList.values());
    }
    
    public boolean isGameAvailable() {
        int size = waitingQueue.size();
        return size >= 2;
    }

    public ChessGame matchPlayers() {
        ChessGame newGame = null;
        if (isGameAvailable()) {
            User user1 = waitingQueue.poll();
            User user2 = waitingQueue.poll();

            newGame = new ChessGame();
            newGame.matchPlayer(user1, user2);

            addChessGame(newGame);
        }
        return newGame;

    }

    public void addChessGame(ChessGame game) {
        chessGameList.put(game.getId(), game);
    }

    public ChessGame getChessGame(String gameId) {
        return chessGameList.get(gameId);
    }

    public void removeChessGame(String gameId) {
        chessGameList.remove(gameId);
    }

}
