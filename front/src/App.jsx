import React from "react";
import { Routes, Route } from 'react-router-dom';
import Main from "./page/Main.jsx";
import UserJoin from "./userPage/UserJoin.jsx";
import UserLogin from "./userPage/UserLogin.jsx";
import PostDetailPage from "./page/PostDetailPage.jsx";
import UploadPage from "./page/UploadPage.jsx";
import PostModifyPage from "./page/PostModifyPage.jsx";

function App() {

  return (
    <div>
        <Routes>
            <Route path="/" element={<Main />} />
            <Route path="/" element={<UserJoin />} />
            <Route path="/login" element={<UserLogin />} />
            <Route path="/postDetail/:postId" element={<PostDetailPage />} />
            <Route path="/postModify/:postId" element={<PostModifyPage />} />
            <Route path="/upload" element={<UploadPage />} />
        </Routes>
    </div>
  )
}

export default App;
