package col.carrot.back.user.chat;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;


@Slf4j
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        this.log.debug("####RecivedMessage :" + payload);

        JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
        JSONObject obj = (JSONObject) parser.parse(payload);
        super.handleTextMessage(session, message);

        String cmd = obj.getAsString("cmd");
        String nickname = obj.getAsString("nickname");

        switch (cmd) {

            case "poll": {
                this.chatService.adUser(nickname, session);
                Map<String, Object> attr = session.getAttributes();
                attr.put("nickname", nickname);
                break;
            }
            case "push": {
                String msg = obj.getAsString("msg");
                this.chatService.addMessage(nickname, msg);
                break;
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Map<String, Object> attr = session.getAttributes();
        String nickname = (String) attr.get("nickname");
        this.log.debug("Websocket Closed : " + nickname);
        this.chatService.removeUser(nickname);
    }
}
