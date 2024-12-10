package col.carrot.back.user.userlogin;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/userLogin")
@AllArgsConstructor
public class UserController {
    //private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        if (id == null) {
            return "/login";
        } else {
            System.out.println("유저 :" + id);
        }
        return "redirect:/";
    }
}
