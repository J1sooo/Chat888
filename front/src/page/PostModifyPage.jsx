import {useEffect, useState} from "react";
import {useParams, useNavigate} from "react-router-dom";
import axios from "axios";

function PostModifyPage() {
    const [postDetail, setPostDetail] = useState({
        title: '',
        content: ''
    });
    const {postId} = useParams();
    const navigate = useNavigate();

    useEffect(()=>{
        const fetchPostDetail = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/findPost/${postId}`);
                setPostDetail(response.data);
            } catch (e) {
                console.log(e);
            }
        }
        fetchPostDetail();
    }, [postId])

    const handleChange = (e) => {
        const {name, value} = e.target;
        setPostDetail(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/modify/${postId}`, postDetail);
            alert('게시물이 수정되었습니다.');
            navigate(`/postDetail/${postId}`);
        } catch (error) {
            console.error('수정 실패:', error);
            alert('게시물 수정에 실패했습니다.');
        }
    };

    return (
        <div style={{marginTop: '80px', padding: '20px'}}>
            <form onSubmit={handleSubmit}>
                <div className="post-detail">
                    {postDetail ? (
                        <>
                            <div>
                                <h1>게시물 제목</h1>
                                <input
                                    type="text"
                                    name="title"
                                    value={postDetail.title || ''}
                                    onChange={handleChange}
                                    style={{
                                        width: '100%',
                                        padding: '8px',
                                        marginBottom: '20px',
                                        fontSize: '16px'
                                    }}
                                />
                            </div>
                            <div>
                                <h1>게시물 내용</h1>
                                <textarea
                                    name="content"
                                    value={postDetail.content || ''}
                                    onChange={handleChange}
                                    style={{
                                        width: '100%',
                                        height: '200px',
                                        padding: '8px',
                                        marginBottom: '20px',
                                        fontSize: '16px'
                                    }}
                                />
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
                                수정 완료
                            </button>
                        </>
                    ) : (
                        <p>게시물이 없습니다</p>
                    )}
                </div>
            </form>
        </div>
    )
}

export default PostModifyPage
