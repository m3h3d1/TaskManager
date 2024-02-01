import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Typography,
  TextField,
  Button,
  Box,
} from "@mui/material";
import Link from "@mui/material/Link";
import axiosInstance from "../../utils/axiosInstance";

const RegistrationForm = () => {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isRegistrationDone, setIsRegistrationDone] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  const handleRegister = (e) => {
    e.preventDefault();

    const data = {
      username: username,
      email: email,
      password: password,
    };

    setIsLoading(true);

    axiosInstance
      .post("/api/register", data)
      .then((resp) => {
        console.log("The Response", resp);
        setIsRegistrationDone(true);
        navigate("/login");
      })
      .catch((err) => {
        console.error("Error", err);
        setError("An error occurred during registration.");
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      p={3}
      bgcolor="background.paper"
    >
      <Typography variant="h4">Registration</Typography>
      {isRegistrationDone && (
        <Typography style={{ color: "green" }}>
          Successfully Registration Done
        </Typography>
      )}
      {isLoading && <Typography>Loading...</Typography>}
      {error && <Typography color="error">{error}</Typography>}

      <form onSubmit={handleRegister}>
        <TextField
          label="User Name"
          variant="outlined"
          value={username}
          placeholder="Enter Username Name"
          onChange={(e) => setUsername(e.target.value)}
          fullWidth
          margin="dense"
        />

        <TextField
          label="Email"
          variant="outlined"
          value={email}
          placeholder="Enter Email"
          onChange={(e) => setEmail(e.target.value)}
          fullWidth
          margin="dense"
        />

        <TextField
          label="Password"
          variant="outlined"
          value={password}
          placeholder="Enter Password"
          type="password"
          onChange={(e) => setPassword(e.target.value)}
          fullWidth
          margin="dense"
        />

        <Button
          type="submit"
          variant="contained"
          color="primary"
          size="large"
          fullWidth
        >
          Register
        </Button>
      </form>

      <Box mt={2}>
        <Typography variant="body2">
          Already have an account?{" "}
          <Link onClick={() => navigate("/login")} underline="always">
            Login here
          </Link>
        </Typography>
      </Box>
    </Box>
  );
};

export default RegistrationForm;