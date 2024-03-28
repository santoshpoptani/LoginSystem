import React,{useState,useEffect} from 'react';
import axios from 'axios';
import {  Link, useNavigate } from 'react-router-dom';


function Profile(){

    const [message , setMessage] = useState("");

    const auth = JSON.parse(localStorage.getItem("auth"));


    const fun = async ()=>{
    //     if(auth.roles.includes("ROLE_ADMIN") || auth.roles.length > 1){
    //     await axios.get(`http://localhost:5001/api/v1/admin`,{
    //         headers:{ Authorization : 'Bearer ' +auth.jwt}
    //     })
    //     .then((res)=>{
    //         console.log(res.data);
    //         setMessage(res.data);
    //     })
        
    // }
    // if(auth.roles.includes("ROLE_MODERATOR") || auth.roles.length === 1){
    //     await axios.get(`http://localhost:5001/api/v1/mod`,{
    //         headers:{ Authorization : 'Bearer ' +auth.jwt}
    //     })
    //     .then((res)=>{
    //         console.log(res.data);
    //         setMessage(res.data);
    //     })
        
    // }
    // if(auth.roles.includes("ROLE_USER") || auth.roles.length === 1){
    //     await axios.get(`http://localhost:5001/api/v1/user`,{
    //         headers:{ Authorization : 'Bearer ' +auth.jwt}
    //     })
    //     .then((res)=>{
    //         console.log(res.data);
    //         setMessage(res.data);
    //     })
        
    // }
    switch (auth != null) {
        case auth.roles.includes("ROLE_ADMIN") || auth.roles.length > 1:
          await axios.get(`http://localhost:5001/api/v1/admin`, {
            headers: { Authorization: 'Bearer ' + auth.jwt }
          }).then((res) => {
            console.log(res.data);
            setMessage(res.data);
          });
          break;
    
        case auth.roles.includes("ROLE_MODERATOR") || auth.roles.length === 1:
          await axios.get(`http://localhost:5001/api/v1/mod`, {
            headers: { Authorization: 'Bearer ' + auth.jwt }
          }).then((res) => {
            console.log(res.data);
            setMessage(res.data);
          });
          break;
    
        case auth.roles.includes("ROLE_USER") || auth.roles.length === 1:
          await axios.get(`http://localhost:5001/api/v1/user`, {
            headers: { Authorization: 'Bearer ' + auth.jwt }
          }).then((res) => {
            console.log(res.data);
            setMessage(res.data);
          });
          break;
    
        default:
          console.log("No role specified or invalid role.");
          break;
      }
        
    }

    useEffect(() => {
        fun()
    },[]);

    const handleUser = async () => {
        await axios.get(`http://localhost:5001/api/v1/user`, {
          headers: { Authorization: 'Bearer ' + auth.jwt }
        }).then((res) => {
          console.log(res.data);
          setMessage(res.data);
        });
      };

      const handleModerator = async () => {
        await axios.get(`http://localhost:5001/api/v1/mod`, {
          headers: { Authorization: 'Bearer ' + auth.jwt }
        }).then((res) => {
          console.log(res.data);
          setMessage(res.data);
        });
      };
    

    return(
        <div>
            <h1>
                {message}
            </h1>
        </div>
    )
}

export default Profile;
// user admin moderator