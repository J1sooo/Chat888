package col.carrot.back.user.userlogin;

import col.carrot.back.user.userlogin.domain.data.LoginData;
import col.carrot.back.user.userlogin.domain.data.ResponseData;
import col.carrot.back.user.userlogin.domain.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

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
    public String checkLogin(HttpSession session) {
        return userService.checkLogin(session);
    }
}
