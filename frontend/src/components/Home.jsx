import axios from "axios";
import React,{useState,useEffect} from "react";

function Home(){

    const [message , setMessage] = useState("");

    const fun = async ()=>{
        await axios.get(`http://localhost:5001/api/v1/all`).then((res) => {
          setMessage(res.data);
        });
        
    }

    useEffect(() => {
        fun()
    },[]);

    return(
        <div>
            <h1>
                {message}
            </h1>

        </div>
    )
}

export default Home;