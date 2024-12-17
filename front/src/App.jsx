import React from "react";
import { Route, Routes } from "react-router-dom";
import User from "./userPage/User.jsx";
import Login from "./userPage/Login.jsx";

function App() {
  return (
      <Routes>
          <Route path="/" element={<User />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      
  );
}

export default App;
