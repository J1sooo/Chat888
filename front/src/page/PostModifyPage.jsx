import {useEffect, useState} from "react";
import {useParams, useNavigate} from "react-router-dom";
import axios from "axios";

function PostModifyPage() {
    const [postDetail, setPostDetail] = useState({
        title: '',
        content: '',
        price: '',
        imageUrl: ''
    });
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState('');
    const {postId} = useParams();
    const navigate = useNavigate();

    useEffect(()=>{
        const fetchPostDetail = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/findPost/${postId}`);
                setPostDetail(response.data);
                if (response.data.imageUrl) {
                    setPreview(response.data.imageUrl);
                }
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
            setPreview(postDetail.imageUrl || '');
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('title', postDetail.title);
        formData.append('content', postDetail.content);
        formData.append('price', parseInt(postDetail.price));
        if (file) {
            formData.append('file', file);
        }

        try {
            const response = await axios.put(
                `http://localhost:8080/modify/${postId}`,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            );

            if (response.data) {
                alert('게시물이 수정되었습니다.');
                navigate(`/postDetail/${postId}`);
            }
        } catch (error) {
            console.error('수정 실패:', error);
            alert('게시물 수정에 실패했습니다: ' + (error.response?.data || error.message));
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
                                <h1>가격</h1>
                                <input
                                    type="number"
                                    name="price"
                                    value={postDetail.price || ''}
                                    onChange={handleChange}
                                    style={{
                                        width: '100%',
                                        padding: '8px',
                                        marginBottom: '20px',
                                        fontSize: '16px'
                                    }}
                                />
                                <div style={{marginBottom: '20px'}}>
                                    <h1>이미지</h1>
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
                                                    objectFit: 'contain',
                                                    marginTop: '10px'
                                                }}
                                            />
                                        </div>
                                    )}
                                </div>
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
                                수정
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