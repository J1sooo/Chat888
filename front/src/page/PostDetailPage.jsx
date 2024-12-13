import {useEffect, useState} from "react";
import {useParams, useNavigate} from "react-router-dom";
import axios from "axios";

function PostDetailPage() {
    const [postDetail, setPostDetail] = useState();
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

    const handleDelete = async () => {
        try {
            await axios.delete(`http://localhost:8080/delete/${postId}`);
            alert('게시물이 삭제되었습니다.');
            navigate('/'); // 목록 페이지로 이동
        } catch (e) {
            console.log(e);
            alert('게시물 삭제에 실패했습니다.');
        }
    }

    return (
        <div>
            <div className="post-detail">
                {postDetail ? (
                    <>
                        <h1>게시물 제목</h1> {postDetail.title},
                        <h1>게시물 내용</h1> {postDetail.content}
                    </>
                ) : (
                    <p>게시물이 없습니다</p>
                )}
            </div>

            <button onClick={handleDelete}> 삭제 </button>
        </div>
    )
}

export default PostDetailPage
