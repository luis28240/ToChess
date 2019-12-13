package com.ToChess.configurations;

import com.ToChess.controllers.WebSocketMatchPlayersHandler;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 *
 * @author Luis
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public WebSocketMatchPlayersHandler webSocketMatchPlayersHandler;

    @Autowired
    public WebSocketConfig(WebSocketMatchPlayersHandler webSocketMatchPlayersHandler) {
        this.webSocketMatchPlayersHandler = webSocketMatchPlayersHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketMatchPlayersHandler, "/match-handler")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
    }

}
