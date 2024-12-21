import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";

function Login() {
    const [userId, setUserId] = useState("");
    const [userPw, setUserPw] = useState("");
    const navigate = useNavigate();
    const location = useLocation();

    // 로그인 상태 체크
    useEffect(() => {
        const checkLoginStatus = async () => {
            try {
                const response = await axios.get("http://localhost:8080/user/checkLogin", {
                    withCredentials: true, // 세션 쿠키를 포함하여 요청
                });

                // 로그인 상태인 경우 메인 페이지로 리디렉션
                if (response.data) {
                    if (location.pathname === "/login") {
                        navigate("/");
                    }
                }
            } catch (error) {
                console.error("로그인 상태 확인 실패:", error);
            }
        };

        checkLoginStatus();
    }, [location.pathname, navigate]);

    const login = async (e) => {
        e.preventDefault();

        const LoginUserData = {
            userId: userId,
            password: userPw,
        };

        try {
            const response = await axios.post(
                "http://localhost:8080/user/login",
                LoginUserData,
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                    withCredentials: true, // 세션 쿠키를 포함하여 요청
                }
            );

            if (response.data.success) {
                // 로그인 성공 시, 세션 쿠키가 자동으로 설정됩니다.
                navigate("/"); // 메인 페이지로 이동
            } else {
                alert("로그인 실패: " + response.data.msg);
            }
        } catch (e) {
            alert("로그인 실패: 서버와의 통신 중 문제가 발생했습니다.");
        }
    };

    return (
        <div
            style={{
                marginTop: "80px",
                padding: "20px",
                maxWidth: "400px",
                margin: "0 auto",
            }}
        >
            <h1 style={{ textAlign: "center", marginBottom: "20px" }}>로그인</h1>
            <form onSubmit={login}>
                <div style={{ marginBottom: "15px" }}>
                    <input
                        type="text"
                        value={userId}
                        onChange={(e) => setUserId(e.target.value)}
                        placeholder="아이디"
                        style={{
                            width: "100%",
                            padding: "10px",
                            fontSize: "16px",
                            borderRadius: "4px",
                            border: "1px solid #ddd",
                        }}
                    />
                </div>
                <div style={{ marginBottom: "15px" }}>
                    <input
                        type="password"
                        value={userPw}
                        onChange={(e) => setUserPw(e.target.value)}
                        placeholder="비밀번호"
                        style={{
                            width: "100%",
                            padding: "10px",
                            fontSize: "16px",
                            borderRadius: "4px",
                            border: "1px solid #ddd",
                        }}
                    />
                </div>
                <button
                    type="submit"
                    style={{
                        width: "100%",
                        padding: "12px",
                        backgroundColor: "#ff6b6b",
                        color: "white",
                        border: "none",
                        borderRadius: "4px",
                        fontSize: "16px",
                        cursor: "pointer",
                    }}
                >
                    로그인
                </button>
            </form>
        </div>
    );
}

export default Login;
