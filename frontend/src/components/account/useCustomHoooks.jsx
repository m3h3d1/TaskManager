import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../utils/axiosInstance";

const UseCustomHooks = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const navigate = useNavigate();

  const [isRegistrationDone, setIsRegistrationDone] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      username: formData.username,
      password: formData.password,
    };

    console.log(data);

    setIsLoading(true);
    axiosInstance
      .post("/api/login", data)
      .then((resp) => {
        console.log("The Response", resp);
        console.log("Response from login ", data);
        const token = resp.data.Authorization;
        const userId = resp.data.userId;
        const email = resp.data.email;
        const username = resp.data.username;
        const role = resp.data.role;
        localStorage.setItem("token", token);
        localStorage.setItem("userId", userId);
        localStorage.setItem("email", email);
        localStorage.setItem("role", role);
        localStorage.setItem("username", username);
        setIsRegistrationDone(true);
        if(role=="ADMIN") {
          navigate("/admin");
        }
        else {
          navigate("/");
        }
      })
      .catch((error) => {
        setError(true);
        if (error.response) {
          const errorMessage = error.response.data.message;
          console.log("Error Response Data:", errorMessage);
        } else {
          // Handle other types of errors
        }
        setTimeout(() => {
          setError(false);
        }, 2000);
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  return {
    handleChange,
    handleSubmit,
    error,
    formData,
    isLoading,
  };
};

export default UseCustomHooks;