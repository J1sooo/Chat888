package col.carrot.back.user.userlogin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String nickname;

    public UserEntity() {

    }

    public UserEntity(String userId, String password, String nickname) {
        super();
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ChatUser [userId=" + userId
                + ", userPwd=" + password
                + ", userName=" + nickname + "]";
    }
}
