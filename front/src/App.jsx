import { Routes, Route } from 'react-router-dom';
import Main from "./page/Main.jsx";
import UploadPage from "./page/UploadPage.jsx";
import DeletePage from "./page/DeletePage.jsx";
function App() {

  return (
    <div>
        <Routes>
            <Route path="/" element={<Main />} />
            <Route path="/upload" element={<UploadPage />} />
            <Route path="/delete" element={<DeletePage />} />
        </Routes>
    </div>
  )
}

export default App
