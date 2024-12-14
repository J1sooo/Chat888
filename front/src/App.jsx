import React from "react";
import { Route, Routes } from "react-router-dom";
import User from "./userPage/User.jsx";

function App() {
  return (
      <Routes>
          <Route path="/" element={<User />} />
        </Routes>
      
  );
}

export default App;
