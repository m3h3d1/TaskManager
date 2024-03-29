package com.mehedi.taskmanager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehedi.taskmanager.config.SpringApplicationContext;
import com.mehedi.taskmanager.model.auths.LoginRequestDTO;
import com.mehedi.taskmanager.model.auths.LoginResponseDTO;
import com.mehedi.taskmanager.model.userdto.UserReadDTO;
import com.mehedi.taskmanager.service.UserService;
import com.mehedi.taskmanager.utilities.constants.TokenConstants;
import com.mehedi.taskmanager.utilities.token.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }

    private final Map<String, Integer> attemptCount = new HashMap<String, Integer>();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequestDTO credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
        } catch (IOException e) {
            writeResponse(response, "Exception while reading credentials", 400);
        }

        attemptCount.put(credentials.getUsername(), attemptCount.getOrDefault(credentials.getUsername(), 0) + 1);
        return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(), credentials.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserReadDTO userReadDto = userService.readByUsername(username);

        if (attemptCount.get(userReadDto.getUsername()) > TokenConstants.MAX_LOGIN_ATTEMPTS_LIMIT) {
            restrictedResponse(response);
            return;
        } else {
            attemptCount.put(userReadDto.getUsername(), 0);
        }

        String userRole = userReadDto.getRole().getRoleName();
        List<String> userRoles = new ArrayList<>();
        userRoles.add("ROLE_"+userRole);
        String accessToken = JWTUtils.generateToken(username, userRoles);

        LoginResponseDTO responseBody = new LoginResponseDTO(userReadDto.getUsername(), accessToken, userRoles);
        writeResponse(response, responseBody, 200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Username or Password wrong. Max failed attempt: "+TokenConstants.MAX_LOGIN_ATTEMPTS_LIMIT);
        writeResponse(response, errorResponse, 400);
    }

    private void restrictedResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Restricted", "Your account has been locked for "+ TokenConstants.MAX_LOGIN_ATTEMPTS_LIMIT +" failed attempts.");
        writeResponse(response, errorResponse, 403);
    }

    private void writeResponse(HttpServletResponse response, Object object, int statusCode){
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try{
            new ObjectMapper().writeValue(response.getWriter(), object);
        }
        catch (IOException ioe){
            log.error("Failed to write in response.", ioe);
        }
    }
}