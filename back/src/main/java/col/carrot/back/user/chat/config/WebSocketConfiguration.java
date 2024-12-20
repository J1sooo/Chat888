package col.carrot.back.user.chat.config;

import col.carrot.back.user.chat.WebSocketHandler;
import col.carrot.back.user.chat.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final ChatService chatService;

    public WebSocketConfiguration(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        this.log.debug("######## RegistereWebSocketHandlers");
        registry.addHandler(new WebSocketHandler(this.chatService), "/chat").setAllowedOrigins("*");
    }
}
