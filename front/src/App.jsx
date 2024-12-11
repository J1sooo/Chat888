import { Routes, Route } from 'react-router-dom';
import Main from "./page/Main.jsx";

function App() {

  return (
    <div>
        <Routes>
            <Route path="/" element={<Main />} />
        </Routes>
    </div>
  )
}

export default App
