import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function UpLoadPage() {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [price, setPrice] = useState('');
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState('');
    const navigate = useNavigate();

    // 로그인 체크
    useEffect(() => {
        const checkLoginStatus = async () => {
            try {
                // 세션을 이용하여 로그인 상태 확인
                const response = await axios.get('http://localhost:8080/user/checkLogin', {
                    withCredentials: true,  // 세션 쿠키를 포함한 요청
                });

                if (!response.data) {
                    // 로그인되지 않은 경우
                    alert('로그인이 필요한 서비스입니다.');
                    navigate('/login');
                }
            } catch (error) {
                console.error('로그인 체크 실패:', error);
                alert('로그인 상태를 확인할 수 없습니다.');
                navigate('/login');
            }
        };

        checkLoginStatus();
    }, [navigate]);

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];

        // 파일 크기 제한 (10MB)
        if (selectedFile && selectedFile.size > 10 * 1024 * 1024) {
            alert('파일 크기는 10MB 이하여야 합니다.');
            return;
        }

        // 이미지 파일 형식 검사
        if (selectedFile && !selectedFile.type.startsWith('image/')) {
            alert('이미지 파일만 업로드 가능합니다.');
            return;
        }

        setFile(selectedFile);

        if (selectedFile) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreview(reader.result);
            };
            reader.readAsDataURL(selectedFile);
        } else {
            setPreview('');
        }
    };

    const validateForm = () => {
        if (!title.trim()) {
            alert('제목을 입력해주세요.');
            return false;
        }
        if (title.length > 40) {
            alert('제목은 40자 이하여야 합니다.');
            return false;
        }
        if (!content.trim()) {
            alert('내용을 입력해주세요.');
            return false;
        }
        if (content.length > 1000) {
            alert('내용은 1000자 이하여야 합니다.');
            return false;
        }
        if (!price) {
            alert('가격을 입력해주세요.');
            return false;
        }
        if (price < 0) {
            alert('가격은 0원 이상이어야 합니다.');
            return false;
        }
        if (price > 100000000) {
            alert('1억원 이하로 입력해주세요.');
            return false;
        }
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('content', content);
        formData.append('price', parseInt(price));
        if (file) {
            formData.append('file', file);
        }

        try {
            await axios.post(
                'http://localhost:8080/createPost',
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    withCredentials: true, // 세션 쿠키를 포함한 요청
                }
            );
            alert('게시물이 등록되었습니다.');
            navigate('/');
        } catch (error) {
            console.error('에러:', error);
            alert('업로드 실패: ' + error.message);
        }
    };

    return (
        <div style={{marginTop: '80px', padding: '20px'}}>
            <form onSubmit={handleSubmit}>
                <div>
                    <h1>중고거래 글쓰기</h1>
                    <input
                        type="text"
                        placeholder="제목 (40자 이내)"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        maxLength={40}
                        style={{
                            width: '100%',
                            padding: '8px',
                            marginBottom: '5px',
                            fontSize: '16px'
                        }}
                    />
                    <div style={{ textAlign: 'right', marginBottom: '20px', color: '#666' }}>
                        {title.length}/40
                    </div>
                    <textarea
                        placeholder="상품에 대한 자세한 정보를 작성해주세요. (1000자 이내)"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        maxLength={1000}
                        style={{
                            width: '100%',
                            height: '200px',
                            padding: '8px',
                            marginBottom: '5px',
                            fontSize: '16px'
                        }}
                    />
                    <div style={{ textAlign: 'right', marginBottom: '20px', color: '#666' }}>
                        {content.length}/1000
                    </div>
                    <div style={{ marginBottom: '20px' }}>
                        <h3>가격</h3>
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                            <input
                                type="number"
                                placeholder="가격을 입력해주세요"
                                value={price}
                                onChange={(e) => setPrice(e.target.value)}
                                min="0"
                                max="100000000"
                                style={{
                                    width: '100%',
                                    padding: '8px',
                                    fontSize: '16px'
                                }}
                            />
                            <span style={{ marginLeft: '10px' }}>원</span>
                        </div>
                    </div>
                    <div style={{marginBottom: '20px'}}>
                        <h3>상품 이미지</h3>
                        <p style={{ color: '#666', marginBottom: '10px' }}>
                            * 상품 이미지는 10MB 이하의 이미지 파일만 등록 가능합니다.
                        </p>
                        <input
                            type="file"
                            accept="image/*"
                            onChange={handleFileChange}
                            style={{marginBottom: '10px'}}
                        />
                        {preview && (
                            <div>
                                <h3>이미지 미리보기</h3>
                                <img
                                    src={preview}
                                    alt="미리보기"
                                    style={{
                                        maxWidth: '300px',
                                        maxHeight: '300px',
                                        objectFit: 'contain'
                                    }}
                                />
                            </div>
                        )}
                    </div>
                    <button
                        type="submit"
                        style={{
                            width: '100%',
                            padding: '15px',
                            backgroundColor: '#ff6b6b',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer',
                            fontSize: '16px',
                            fontWeight: 'bold'
                        }}
                    >
                        등록하기
                    </button>
                </div>
            </form>
        </div>
    );
}

export default UpLoadPage;
