import Form from "./Form";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const LogIn = () => {
  const navigate = useNavigate();

  // API Base URL
  const API_BASE_URL = "http://localhost:8080/Oline_Shopping_Platform_war_exploded";

  // Check session on component mount
  useEffect(() => {
    axios
      .get(`${API_BASE_URL}/checkSession`, { withCredentials: true })
      .then((response) => {
        console.log("Session Check Response:", response.data);
        if (response.data.authenticated) {
          navigate("/"); // Redirect to the homepage if authenticated
        }
      })
      .catch((error) => {
        console.error("Session Check Error:", error);
      });
  }, [navigate]);

  // Handle login logic
  const handleLogin = (email, password) => {
    axios
      .post(
        `${API_BASE_URL}/LogIn`,
        { email, password },
        { withCredentials: true } // Send cookies
      )
      .then((response) => {
        console.log("Login Response:", response.data);
        if (response.data.success) {
          navigate("/"); // Redirect to the homepage on success
        } else {
          alert(response.data.message || "Invalid email or password");
        }
      })
      .catch((error) => {
        console.error("Login Error:", error);
        alert("Failed to login. Please check your connection and try again.");
      });
  };

  // Render form
  return <Form signup={true} Port={"/signUp"} handleSubmit={handleLogin} />;
};

export default LogIn;
