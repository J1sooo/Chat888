import React, { useState } from 'react';
import axios from 'axios';

function UpLoadPage() {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        // FormData 대신 객체로 직접 전송
        const postData = {
            title: title,
            content: content
        };

        try {
            await axios.post('http://localhost:8080/createPost',
                postData,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            alert('업로드 성공');
        } catch (error) {
            console.error('에러:', error);
            alert('업로드 실패: ' + error.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="제목"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />
            <textarea
                placeholder="내용"
                value={content}
                onChange={(e) => setContent(e.target.value)}
            />
            <button type="submit">업로드</button>
        </form>
    );
}

export default UpLoadPage;