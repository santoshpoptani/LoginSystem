import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import React,{useState} from 'react'
import Register from "./Register.jsx";
import Login from "./Login.jsx";
import Profile from "./Profile.jsx";
import Navigation from "./Navigation.jsx";
import Home from "./Home.jsx";


function App() {
  const[isLoggedIn, setIsLoggedIn] = useState(false);

  const handelData =(data)=> {
    setIsLoggedIn(data)
    console.log(isLoggedIn)
    console.log(data)
  }
  return (
    <>
      <Router>
        <Navigation/>
        <Routes>
        <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </Router>
    </>
  );
}

export default App
