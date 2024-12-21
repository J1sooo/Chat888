import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

function Navibar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [nickname, setNickname] = useState(''); // 닉네임 상태 추가
    const navigate = useNavigate();

    useEffect(() => {
        const checkLoginStatus = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user/checkLogin', {
                    withCredentials: true, // 세션 쿠키를 포함하여 요청
                });

                if (response.data) {
                    setIsLoggedIn(true);
                    setNickname(response.data.nickname || '');
                } else {
                    setIsLoggedIn(false);
                    setNickname('');
                }
            } catch (error) {
                console.error("로그인 상태 확인 실패:", error);
                setIsLoggedIn(false);
                setNickname('');
            }
        };

        checkLoginStatus();
    }, []); // 컴포넌트가 처음 렌더링될 때 실행

    const handleLogout = async () => {
        try {
            await axios.get('http://localhost:8080/user/logout', {
                withCredentials: true, // 세션 쿠키를 포함하여 요청
            });
            setIsLoggedIn(false);
            setNickname('');
            navigate('/');
        } catch (error) {
            console.error("로그아웃 실패:", error);
        }
    };

    const styles = {
        navbar: {
            width: '100%',
            height: '65px',
            backgroundColor: 'white',
            borderBottom: '1px solid #e9ecef',
            position: 'fixed',
            top: 0,
            left: 0,
            zIndex: 1000,
        },
        container: {
            maxWidth: '1200px',
            height: '100%',
            margin: '0 auto',
            padding: '0 20px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
        },
        logo: {
            fontSize: '24px',
            fontWeight: 'bold',
            color: '#ff6b6b',
            textDecoration: 'none',
            flexShrink: 0,
        },
        searchContainer: {
            flex: 1,
            maxWidth: '500px',
            margin: '0 20px',
        },
        searchForm: {
            display: 'flex',
            alignItems: 'center',
            backgroundColor: '#f8f9fa',
            borderRadius: '4px',
            overflow: 'hidden',
        },
        searchInput: {
            flex: 1,
            padding: '10px 15px',
            border: 'none',
            background: 'transparent',
            fontSize: '16px',
            outline: 'none',
        },
        searchButton: {
            display: 'flex',
            alignItems: 'center',
            padding: '10px 15px',
            background: 'none',
            border: 'none',
            cursor: 'pointer',
            color: '#868e96',
        },
        navButtons: {
            display: 'flex',
            alignItems: 'center',
        },
        loginButton: {
            padding: '8px 16px',
            backgroundColor: '#ff6b6b',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            textDecoration: 'none',
            fontWeight: 600,
            cursor: 'pointer',
        },
    };

    return (
        <nav style={styles.navbar}>
            <div style={styles.container}>
                {/* 왼쪽: 로고 */}
                <Link to="/" style={styles.logo}>
                    carrotMarket
                </Link>

                {/* 가운데: 검색 폼 */}
                <div style={styles.searchContainer}>
                    <div style={styles.searchForm}>
                        <input
                            type="text"
                            placeholder="검색어를 입력해주세요"
                            style={styles.searchInput}
                        />
                        <button style={styles.searchButton}>
                            <span>검색</span>
                        </button>
                    </div>
                </div>

                {/* 오른쪽: 로그인/닉네임 및 로그아웃 버튼 */}
                <div style={styles.navButtons}>
                    {isLoggedIn ? (
                        <>
                            <span
                                style={{
                                    marginRight: '15px',
                                    color: '#4a4a4a',
                                    fontSize: '14px',
                                }}
                            >
                                {nickname}님
                            </span>
                            <button
                                onClick={handleLogout}
                                style={{
                                    ...styles.loginButton,
                                    backgroundColor: '#e9ecef',
                                    color: '#495057',
                                }}
                            >
                                로그아웃
                            </button>
                        </>
                    ) : (
                        <Link to="/login" style={styles.loginButton}>
                            로그인
                        </Link>
                    )}
                </div>
            </div>
        </nav>
    );
}

export default Navibar;
