import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const [username, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [roles,  setRoles] = useState([]); 
  const [message, setMessage] = useState("");

  const navigate =useNavigate()

  const handleRoleChange = (e) => {
    const { value, checked } = e.target;
    if (checked) {
      setRoles([...roles, value]);
    } else {
      setRoles(roles.filter((role) => role !== value));
    }
  };

  const handlePost = () => {
    axios.post('http://localhost:5001/api/auth/signup', {
      username: username,
      email: email,
      password: password,
      role: roles,
    })
      .then((res) =>{
        console.log(res.data)
        setMessage(res.data.message);
        if (res.data) {
            navigate('/login')
        }
        else{
            navigate('/')
        }
    })
      .catch((error) => console.error('Error:', error));
  };

  return (
    <div>
      <h2>Register</h2>
      <form>
        <div>
          <label htmlFor="username">Username:</label>
          <input type="text" id="username" name="username" value={username} onChange={(e)=>setUserName(e.target.value)} />
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input type="email" id="email" name="email" value={email} onChange={(e)=>setEmail(e.target.value)} />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input type="password" id="password" name="password" value={password} onChange={(e)=>setPassword(e.target.value)} />
        </div>
        <div>
          <label>Roles:</label>
          <label htmlFor="admin">Admin</label>
          <input type="checkbox" id="admin" name="admin" value="admin" checked={roles.includes('admin')} onChange={handleRoleChange} />
          <label htmlFor="moderator">Moderator</label>
          <input type="checkbox" id="moderator" name="moderator" value="mod" checked={roles.includes('mod')} onChange={handleRoleChange} />
        </div>
        <button type="button" onClick={handlePost}>Submit</button>
        <p> {message}</p>
      </form>
    </div>
  );
};

export default Register;
