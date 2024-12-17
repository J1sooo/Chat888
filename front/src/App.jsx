import React from "react";
import { Route, Routes } from "react-router-dom";
import UserJoin from "./userPage/UserJoin.jsx";
import UserLogin from "./userPage/UserLogin.jsx";

function App() {
  return (
      <Routes>
          <Route path="/" element={<UserJoin />} />
          <Route path="/login" element={<UserLogin />} />
        </Routes>
      
  );
}

export default App;
