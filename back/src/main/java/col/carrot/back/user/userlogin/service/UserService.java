package col.carrot.back.user.userlogin.service;

import col.carrot.back.user.userlogin.UserEntity;
import col.carrot.back.user.userlogin.UserRepository;
import col.carrot.back.user.userlogin.data.LoginData;
import col.carrot.back.user.userlogin.data.ResponseData;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public ResponseData login(LoginData loginData, HttpSession session) {
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

        session.setAttribute("userId", userId);
        return new ResponseData(true, "로그인 되었습니다");
    }

    public ResponseData join(UserEntity userEntity) {

        boolean exists = this.userRepository.existsByUserId(userEntity.getUserId());
        if (exists) {
            return new ResponseData(false, "이미 존재하는 ID입니다.");
        }

        this.userRepository.save(userEntity);

        return new ResponseData(true, "회원가입에 성공하셨습니다!");
    }

    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "로그아웃 성공";
    }

    public String checkLogin(HttpSession session) {
        return (String) session.getAttribute("userId");
    }
}
