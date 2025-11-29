package com.example.BanqueApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
/**
 * Cette annotation active le WebSocket message broker dans Spring.
 * En clair, elle dit à Spring :
 * “Active le support WebSocket et ajoute une couche de messagerie STOMP.”
 */
/**
 * Cette classe implémente l’interface WebSocketMessageBrokerConfigurer, ce qui
 * permet de surcharger certaines méthodes pour personnaliser
 * la configuration WebSocket/STOM
 */
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Méthode permettant de configurer
     * le broker de messages, c’est-à-dire le système qui transporte les messages.
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
         /**
         * Activer un broker de messages simple en mémoire
          * Ce broker distribue les messages envoyés vers des destinations :
          * /topic → messages “broadcast” (pub/sub).
          * /queue → messages point-à-point.
          * Les clients s’abonnent à ces destinations
         * */
        config.enableSimpleBroker("/topic", "/queue");
        /**
         * Préfixe pour les messages envoyés depuis le client
          */
        config.setApplicationDestinationPrefixes("/app");//Indique le préfixe des destinations envoyées depuis le client vers le serveur.
        /**
         * Toutes les routes commençant par /app seront gérées par
         * les contrôleurs Spring (méthodes annotées par @MessageMapping).
         * exemple coté client : stompClient.send("/app/chat", {}, "message");
         */
    }

    /**
     * Méthode permettant de définir les points d’entrée WebSocket pour les clients.
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint WebSocket pour connexion
        registry.addEndpoint("/ws")// Déclare un endpoint WebSocket accessible à l’URL : /ws
                //Les clients s’y connectent via un handshake WebSocket :new SockJS("/ws");
                .setAllowedOriginPatterns("*")//Autorise les connexions depuis n’importe quelle origine (CORS).
                //(pratique pendant le développement, moins sûr en production)
                .withSockJS();//Active SockJS comme fallback si le navigateur ne supporte pas WebSocket.
        /**
         * SockJS peut utiliser :
         * polling
         * long-polling
         * streaming
         * Cela assure une compatibilité maximale.
         */
    }

    /**
     * On dit à Spring : “Active WebSocket + STOMP”.
     * On définit les routes côté client (/app).
     * On définit les routes côté serveur (/topic, /queue).
     * On expose un endpoint /ws auquel les clients se connectent.
     * On active SockJS pour compatibilité maximale.
     */
}
