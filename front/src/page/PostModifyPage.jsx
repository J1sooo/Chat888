import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

function PostModifyPage() {
    const [postDetail, setPostDetail] = useState();
    const {postId} = useParams();

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
        </div>
    )
}

export default PostModifyPage
