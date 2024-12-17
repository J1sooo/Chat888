import {useState} from "react";
import axios from "axios";

function Login() {
    const [userId, setUserId] = useState("");
    const [userPw, setUserPw] = useState("");

    const login = async (e) => {
        e.preventDefault();

        const LoginUserData = {
            userId: userId,
            password: userPw,
        };

        console.log('로그인 시도:', LoginUserData);

        try {
            const response = await axios.post(
                'http://localhost:8080/user/login',
                LoginUserData,
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    withCredentials: true
                }
            );
            
            console.log('서버 응답:', response.data);
            
            if (response.data.success) {
                alert('로그인 성공: ' + response.data.msg);
                
                // 로그인 성공 후 세션 체크
                const checkResponse = await axios.get(
                    'http://localhost:8080/user/checkLogin',
                    { withCredentials: true }
                );
                console.log('세션 체크 결과:', checkResponse.data);
            } else {
                alert('로그인 실패: ' + response.data.msg);
            }
        } catch (e) {
            console.log('에러 발생:', e.response);
            alert("로그인 실패: " + (e.response?.data?.msg || "알 수 없는 오류가 발생했습니다."));
        }
    }

    return (
        <div>
            <div>
                <form>
                    <input type="text" value={userId} onChange={(e) => setUserId(e.target.value)}/>
                    <input type="password" value={userPw} onChange={(e) => setUserPw(e.target.value)}/>
                    <button type="button" onClick={login}>로그인</button>
                </form>
            </div>
        </div>
    )
}

export default Login;