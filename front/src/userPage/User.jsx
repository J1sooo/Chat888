import {useState} from "react";
import axios from "axios";

function User() {
    const [userId, setUserId] = useState();
    const [userPw, setUserPw] = useState();
    const [userNickname, setUserNickname] = useState();



    const handleClick = async (e) => {
        e.preventDefault();

        const postUserData = {
            userId: userId,
            password: userPw,
            nickname: userNickname,
        };

        try {
            const response = await axios.post('http://localhost:8080/user/join', postUserData,
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
            alert('회원가입 성공');
        } catch (e) {
            console.log(e);
            alert('회원가입 실패');
        }
    }

    return(
        <div>
            <form>
                <input type="text" value={userId} onChange={(e) => setUserId(e.target.value)} />
                <input type="password" value={userPw} onChange={(e) => setUserPw(e.target.value)} />
                <input type="text" value={userNickname} onChange={(e) => setUserNickname(e.target.value)} />
                <button type="button" onClick={handleClick}>회원가입</button>
            </form>
        </div>
    )
}

export default User;