import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: "http://localhost:8181",
  timeout: 3000,
});

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  console.log("axiosInstance", localStorage);

  if (token) {
    console.log("Token found:", token);
    config.headers.Authorization = `Bearer ${token}`;
  } else {
    console.log("Token not found");
  }

  return config;
});

export default axiosInstance;
