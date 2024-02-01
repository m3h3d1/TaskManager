import { Navigate, Outlet } from "react-router-dom";
import {
  getRole,
  isAdmin,
  isUser,
  isLogged,
} from "./accountInfo";

export const Authenticate = ({ requiredRole }) => {
  console.log("Required role: " + requiredRole + ", user role: " + getRole());

  if (
    (requiredRole === "ANY" ||
      requiredRole === "USER" ||
      requiredRole === "ADMIN") &&
    !isLogged()
  ) {
    return <Navigate to={"/login"} />;
  }

  if (requiredRole === "ADMIN" && isUser()) {
    return <Navigate to={"/"} />;
  }

  if (requiredRole === "USER" && isAdmin()) {
    return <Navigate to={"/"} />;
  }

  console.log("Serving user the contents.");
  return <Outlet />;
};

export default Authenticate;
