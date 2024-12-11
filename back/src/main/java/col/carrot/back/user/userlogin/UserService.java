package col.carrot.back.user.userlogin;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * 로그인 처리
     * 성공시 UserEntity 반환, 실패시 null 반환
     */
    public UserEntity login(String userId, String password) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        
        // 사용자가 존재하지 않는 경우
        if (user.isEmpty()) {
            return null;
        }

        UserEntity userEntity = user.get();
        
        // 비밀번호가 일치하지 않는 경우
        if (!userEntity.getPassword().equals(password)) {
            return null;
        }

        return userEntity;
    }

    /*
     * Id 중복체크
     */
    public boolean existLogin(String userId) {
        return userRepository.existsByUserId(userId);
    }

}
