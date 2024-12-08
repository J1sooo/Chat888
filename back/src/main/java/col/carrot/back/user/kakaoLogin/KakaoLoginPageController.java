package col.carrot.back.user.kakaoLogin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginPageController {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${REDIRECT_URL}")
    private String redirectUrl;

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId
                + "&redirect_uri=" + redirectUrl;
        model.addAttribute("location", location);
        return "login";
    }
}
