package col.carrot.back.user.kakaoLogin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import col.carrot.back.user.kakaoLogin.domain.KakaoUserInfoResoponseDto;
import col.carrot.back.user.userlogin.domain.UserEntity;
import col.carrot.back.user.userlogin.domain.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaoService {

    @Value("${CLIENT_ID}")
    private String clientId;

    private final UserRepository userRepository;

    public KakaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getAccessTokenFromKakao(String code) {
        try {
            String kakaoUrl = "https://kauth.kakao.com/oauth/token";
            String parameters = String.format("grant_type=authorization_code&client_id=%s&code=%s", clientId, code);

            URL url = new URL(kakaoUrl + "?" + parameters);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(result.toString());

            String accessToken = (String) jsonObj.get("access_token");
            String refreshToken = (String) jsonObj.get("refresh_token");
            String scope = (String) jsonObj.get("scope");

            log.info(" [Kakao Service] Access Token ------> {}", accessToken);
            log.info(" [Kakao Service] Refresh Token ------> {}", refreshToken);
            log.info(" [Kakao Service] Scope ------> {}", scope);

            return accessToken;

        } catch (Exception e) {
            log.error("카카오 토큰 받기 실패: ", e);
            throw new RuntimeException("카카오 로그인 처리 중 오류가 발생했습니다.");
        }
    }

    public KakaoUserInfoResoponseDto getUserInfo(String accessToken) {
        try {
            String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

            URL url = new URL(kakaoUserInfoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // HTTP 헤더 설정
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 응답 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // JSON 파싱
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(result.toString());

            // DTO 생성 및 데이터 매핑
            KakaoUserInfoResoponseDto userInfo = new KakaoUserInfoResoponseDto();
            userInfo.setId((Long) jsonObj.get("id"));

            JSONObject kakaoAccount = (JSONObject) jsonObj.get("kakao_account");
            if (kakaoAccount != null) {
                KakaoUserInfoResoponseDto.KakaoAccount account = new KakaoUserInfoResoponseDto.KakaoAccount();
                JSONObject profile = (JSONObject) kakaoAccount.get("profile");

                if (profile != null) {
                    KakaoUserInfoResoponseDto.Profile userProfile = new KakaoUserInfoResoponseDto.Profile();
                    userProfile.setNickname((String) profile.get("nickname"));
                    account.setProfile(userProfile);
                }

                userInfo.setKakao_account(account);
            }

            // 사용자 정보 저장 로직 추가
            UserEntity user = new UserEntity();
            user.setId(userInfo.getId());
            user.setNickname(userInfo.getKakao_account().getProfile().getNickname());

            // 기존 사용자가 없을 경우에만 저장
            userRepository.findById(user.getId())
                    .orElseGet(() -> userRepository.save(user));

            log.info(" [Kakao Service] User Info ------> {}", userInfo);
            return userInfo;

        } catch (Exception e) {
            log.error("카카오 사용자 정보 받기 실패: ", e);
            throw new RuntimeException("카카오 사용자 정보 조회 중 오류가 발생했습니다.");
        }
    }
}
