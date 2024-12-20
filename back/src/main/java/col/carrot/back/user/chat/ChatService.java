package col.carrot.back.user.chat;

import col.carrot.back.user.chat.domain.ChatEntity;
import col.carrot.back.user.chat.domain.ChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    public void adUser(String nickname, WebSocketSession session) {
        this.log.debug("new chat : " + nickname);
        this.users.put(nickname, session);

        this.addMessage(nickname,"채팅이 시작되었습니다");
    }

    public void removeUser(String nickname) {
        this.users.remove(nickname);

        this.addMessage(nickname,"채팅이 종료되었습니다");
    }

    public void addMessage(String nickname, String msg) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setNickname(nickname);
        chatEntity.setContent(msg);
        chatEntity.setChatCretime(new Date());
        this.chatRepository.save(chatEntity);

        JSONObject obj = new JSONObject();
        obj.put("cmd", "message");
        obj.put("nickname", nickname);
        obj.put("msg",msg);

        TextMessage message = new TextMessage(obj.toJSONString());
        Set<Map.Entry<String, WebSocketSession>> entrySet = this.users.entrySet();
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            WebSocketSession session = entry.getValue();
            try {
                session.sendMessage(message);
                this.log.debug(entry.getKey() + "added");
            } catch (Exception e) {
                this.log.error("error : " + e.getMessage());
            }
        }
    }
}
