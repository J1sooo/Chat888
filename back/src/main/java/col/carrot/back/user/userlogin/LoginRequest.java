package col.carrot.back.user.userlogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userId;
    private String password;
} 