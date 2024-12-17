package col.carrot.back.user.kakaoLogin;


import col.carrot.back.user.kakaoLogin.domain.KakaoUserInfoResoponseDto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    public KakaoLoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResoponseDto userInfo = kakaoService.getUserInfo(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}