import React from "react";
import { Route, Routes } from "react-router-dom";
import Main from "./page/Main.jsx";
import ListPage from "./page/ListPage.jsx";

function App() {
  return (
      <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/list" element={<ListPage />} />
        </Routes>
      
  );
}

export default App;
