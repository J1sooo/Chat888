import { useState, useEffect } from 'react';
import axios from 'axios';

function ListPage() {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/postAll`);
                setPosts(response.data);
                console.log(response.data);
            } catch (error) {
                console.error('게시물을 불러오는데 실패했습니다:', error);
            }
        };

        fetchPosts();
    }, []);

    return (
        <div>
            <h1>게시물 목록</h1>
            <div className="posts-container">
                {posts.map((post) => (
                    <div key={post.id} className="post-item">
                        <h2>{post.title}</h2>
                        <p>{post.content}</p>
                        {/*{post.imageUrl && <img src={post.imageUrl} alt="게시물 이미지" />}*/}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ListPage;
