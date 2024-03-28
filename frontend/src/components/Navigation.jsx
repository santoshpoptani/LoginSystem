import { Link , useLocation} from "react-router-dom";
import React from "react";



const Navigation=()=> {
    const auth = JSON.parse(localStorage.getItem('auth'));
      useLocation();
    const handleLogout = ()=>{
        localStorage.removeItem('auth');
    }

  return (
    <div>
      <nav>
        {auth != null ? (
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li onClick={handleLogout}>
              <Link to="/register">Logout</Link>
            </li>
          </ul>
        ) : (
          <ul>
             <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/login">Login</Link>
            </li>
            <li>
              <Link to="/register">Register</Link>
            </li>
           
          </ul>
        )}
      </nav>
    </div>
  );
}

export default Navigation;
