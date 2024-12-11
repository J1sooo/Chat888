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
    Id 중복체크
     */
    public boolean existLogin(String userId){
        return userRepository.existsByUserId(userId);
    }


//    /*
//    로그인
//     */
//    public UserEntity login(String userId) {
//        Optional<UserEntity> user = userRepository.findByUserId(login().getUserId());
//
//        //id가 없으면 null
//        if(user.isEmpty()) {
//            return null;
//        }
//
//        UserEntity userEntity = user.get();
//
//        //user의 비밀번호가 틀리면 null
//        if (!userEntity.getPassword().equals(login().getPassword())) {
//            return null;
//        }
//
//        return userEntity;
//    }

}