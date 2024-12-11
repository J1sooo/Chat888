package col.carrot.back.user.userlogin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/userLogin")
@RequiredArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        UserEntity user = userService.login(loginRequest.getUserId(), loginRequest.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 세션 생성 및 사용자 정보 저장
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getUserId());

        return ResponseEntity.ok()
                .body(Map.of(
                        "message", "로그인 성공",
                        "userId", user.getUserId(),
                        "nickname", user.getNickname()
                ));
    }

//    @GetMapping("")
//    public String index(Model model, HttpSession session) {
//        String userId = (String) session.getAttribute("userId");
//        if(userId == null) {
//            return "login";
//        }
//        Optional<UserEntity> user = userRepository.findByUserId(userId);
//        if(user.isEmpty()) {
//            return "login";
//        }
//
//        return "main";
//    }

//    @PostMapping(path = {"login"}, consumes = {"application/json"})
//    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password,
//                        HttpServletRequest request,Model model) {
//        this.log.debug("userId" + userId + "password" + password);
//
//        Optional<UserEntity> user = this.userRepository.findByUserId(userId);
//        if(user.isEmpty()) {
//            return "login";
//        }
//        UserEntity userEntity = user.get();
//        if(!userEntity.getPassword().equals(password) == false) {
//            return "login";
//        }
//        HttpSession session = request.getSession(false);
//        if(session != null) {
//            session.invalidate();
//        }
//        session = request.getSession(true);
//        session.setAttribute("userId", userId);
//
//        return "redirect:main";
//    }

    @GetMapping("joinform")
    public String joinForm() {
        return "joinform";
    }

    @PostMapping(path ={"join"}, consumes = {"application/json"})
    public String join(UserEntity userEntity) {
        this.log.debug("" + userEntity);
        this.userRepository.save(userEntity);

        return "redirect:login";
    }



}
