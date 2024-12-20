package col.carrot.back.user.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long msgId;

    private String nickname;
    private String content;
    @Temporal( TemporalType.TIMESTAMP)
    private Date chatCretime;

}
