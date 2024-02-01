const isLogged = () => {
  const token = localStorage.getItem("token");
  return !!token;
};

const getRole = () => {
  return localStorage.getItem("role");
};

const isAdmin = () => {
  const role = localStorage.getItem("role");
  return role === "ADMIN";
};

const isUser = () => {
  const role = localStorage.getItem("role");
  return role === "USER";
};

const getUserId = () => {
    return localStorage.getItem("userId");
};

const getEmail = () => {
  return localStorage.getItem("email");
};

const clearInfo = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  localStorage.removeItem("email");
};

export { isLogged, isAdmin, isUser, getEmail, clearInfo, getRole };
