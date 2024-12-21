package col.carrot.back.user.userlogin;

import col.carrot.back.user.userlogin.domain.UserEntity;
import col.carrot.back.user.userlogin.domain.UserRepository;
import col.carrot.back.user.userlogin.domain.data.LoginData;
import col.carrot.back.user.userlogin.domain.data.ResponseData;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;  // UserRepository 필드 추가

    // 생성자에서 UserRepository와 UserService를 주입
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseData loginUser(@RequestBody LoginData loginData, HttpSession session) {
        return userService.login(loginData, session);
    }

    @PostMapping("/join")
    public ResponseData saveUser(@RequestBody UserEntity userEntity, Errors errors) {
        if (errors.hasErrors()) {
            List<ObjectError> objErrs = errors.getAllErrors();
            this.log.debug("Errors: " + errors);
            this.log.debug("List<ObjectError>>: " + objErrs);
            StringBuilder message = new StringBuilder();
            for (ObjectError objErr : objErrs) {
                message.append(objErr.getDefaultMessage()).append("\n");
            }
            return new ResponseData(false, message.toString().trim());
        }
        return userService.join(userEntity);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return userService.logout(session);
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        // 세션에서 userId를 가져옴
        String userId = (String) session.getAttribute("userId");

        // userId가 세션에 존재한다면, 사용자 정보를 가져옴
        if (userId != null) {
            Optional<UserEntity> user = userRepository.findByUserId(userId);  // UserRepository 사용
            if (user.isPresent()) {
                // 반환할 데이터 맵 생성
                Map<String, Object> response = new HashMap<>();
                response.put("nickname", user.get().getNickname());  // 닉네임 포함

                // 성공적으로 사용자 정보를 반환
                return ResponseEntity.ok(response);
            }
        }

        // 세션에 사용자 정보가 없거나, 로그인되지 않은 경우
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
