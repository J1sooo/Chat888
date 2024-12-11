package col.carrot.back.user.userlogin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/userLogin")
@AllArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null) {
            return "login";
        }
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(user.isEmpty()) {
            return "login";
        }

        return "main";
    }

    @PostMapping(path = {"login"}, consumes = {"application/json"})
    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password,
                        HttpServletRequest request,Model model) {
        this.log.debug("userId" + userId + "password" + password);

        Optional<UserEntity> user = this.userRepository.findByUserId(userId);
        if(user.isEmpty()) {
            return "login";
        }
        UserEntity userEntity = user.get();
        if(!userEntity.getPassword().equals(password) == false) {
            return "login";
        }
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        session = request.getSession(true);
        session.setAttribute("userId", userId);

        return "redirect:main";
    }

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
