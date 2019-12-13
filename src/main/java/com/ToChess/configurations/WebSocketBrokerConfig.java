package com.ToChess.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 
 * @author Luis
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer{

    public static final String TOPIC_GAMES = "/topic";
    public static final String APP_PATH = "/app";
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(TOPIC_GAMES);
        registry.setApplicationDestinationPrefixes(APP_PATH, TOPIC_GAMES);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/toChess-games")
                .addInterceptors(new HttpSessionHandshakeInterceptor()) //<-Añadimos la union entre los atributos de la Session y la del 
                .withSockJS();                                          //  WebSocket session
    }
    
}
