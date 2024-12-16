import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function UpLoadPage() {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [price, setPrice] = useState('');
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState('');
    const navigate = useNavigate();

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];
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

    const handleSubmit = async (e) => {
        e.preventDefault();


            const formData = new FormData();
            formData.append('title', title);
            formData.append('content', content);
            formData.append('price', price);
            if (file) {
                formData.append('file', file);
            }

        try {
            await axios.post(
                'http://localhost:8080/createPost',
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            );
            alert('업로드 성공');
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
                        <h1>게시물 작성</h1>
                        <input
                            type="text"
                            placeholder="제목"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            style={{
                                width: '100%',
                                padding: '8px',
                                marginBottom: '20px',
                                fontSize: '16px'
                            }}
                        />
                        <textarea
                            placeholder="내용"
                            value={content}
                            onChange={(e) => setContent(e.target.value)}
                            style={{
                                width: '100%',
                                height: '200px',
                                padding: '8px',
                                marginBottom: '20px',
                                fontSize: '16px'
                            }}
                        />
                        <input
                            type="number"
                            placeholder="가격"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            style={{
                                width: '100%',
                                padding: '8px',
                                marginBottom: '20px',
                                fontSize: '16px'
                            }}
                        />
                        <div style={{marginBottom: '20px'}}>
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
                                padding: '10px 20px',
                                backgroundColor: '#ff6b6b',
                                color: 'white',
                                border: 'none',
                                borderRadius: '4px',
                                cursor: 'pointer'
                            }}
                        >
                            업로드
                        </button>
                    </div>
                </form>
            </div>
            );
            }

            export default UpLoadPage;