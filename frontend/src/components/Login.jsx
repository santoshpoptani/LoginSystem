import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';


const  Login=()=> {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate= useNavigate();
  
    const handleLogin = () => {
      axios.post(`http://localhost:5001/api/auth/signin`,{
        username:username,
        password:password
      }).then((res)=>{
        console.log(res.data);
        const data={
                    "jwt":res.data.token,
                    "roles": res.data.roles,
                    isLoggedIn:true   
                 }
        localStorage.setItem("auth",JSON.stringify(data));
        navigate("/profile");
      })
      .catch((err) => {
        console.log(err)
      })
    };
  
    return (
      <div>
        <h2>Login</h2>
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button onClick={handleLogin}>Login</button>
      </div>
    );
  };
  
  export default Login;