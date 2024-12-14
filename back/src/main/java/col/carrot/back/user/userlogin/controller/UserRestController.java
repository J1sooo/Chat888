package col.carrot.back.user.userlogin.controller;

import col.carrot.back.user.userlogin.UserEntity;
import col.carrot.back.user.userlogin.UserRepository;
import col.carrot.back.user.userlogin.data.LoginData;
import col.carrot.back.user.userlogin.data.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final Logger log = LoggerFactory.getLogger(UserRestController.class);
    private final UserRepository userRepository;

    @PostMapping(path = "/login")
    public ResponseData login(@RequestBody LoginData loginData, HttpServletRequest request) {
        String userId = loginData.getUserId();
        String password = loginData.getPassword();
        Optional<UserEntity> user = this.userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            return new ResponseData(false, "유저가 존재하지 않습니다");
        }
        UserEntity userEntity = user.get();
        if (!userEntity.getPassword().equals(password)) {
            return new ResponseData(false, "비밀번호가 틀렸습니다");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
        return new ResponseData(true, "로그인 되었습니다");
    }

    @PostMapping(path = "/join")
    public ResponseData join(@RequestBody UserEntity userEntity, Errors errors) {
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
        boolean exists = this.userRepository.existsByUserId(userEntity.getUserId());
        if (exists) {
            return new ResponseData(false, "이미 존재하는 ID입니다.");
        }

        this.userRepository.save(userEntity);

        return new ResponseData(true, "회원가입에 성공하셨습니다!");
    }

}
