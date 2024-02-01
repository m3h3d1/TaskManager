import { Route, Routes } from "react-router-dom";
import "./App.scss";
// import HomePage from "./pages/homePage";
import RegistrationPage from "./components/account/registrationPage";
import LoginPage from "./components/account/loginPage";
import Header from "./components/common/header";
import NotFoundPage from "./components/notFoundPage";
import Authenticate from "./components/account/authenticate";
import Footer from "./components/common/footer";

function App() {
  
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" exact element={<LoginPage />} />
        <Route path="/register" element={<RegistrationPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route element={<Authenticate requiredRole={"ANY"} />}>
          {/* <Route path="/users/:userId" element={<UserDetails />} /> */}
        </Route>
        <Route element={<Authenticate requiredRole={"ADMIN"} />} >
          {/* <Route path="/admin" element={<AdminPage />} /> */}
        </Route>
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
