package col.carrot.back.user.kakaoLogin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoResoponseDto {
    private Long id;
    private KakaoAccount kakao_account;

    @Getter
    @Setter
    public static class KakaoAccount {
        private Profile profile;
    }

    @Getter
    @Setter
    public static class Profile {
        private String nickname;
    }
}