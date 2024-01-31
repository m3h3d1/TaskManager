package com.mehedi.taskmanager.validation;

import com.mehedi.taskmanager.entity.Role;
import com.mehedi.taskmanager.entity.User;
import com.mehedi.taskmanager.exception.InvalidEntityException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    private static final List<String> RULES = new ArrayList<>();
    static {
        RULES.add("User ID must be non-negative");
        RULES.add("Username must be at least 4 characters long and no special characters are allowed except for space");
        RULES.add("The email field must be a valid email");
        RULES.add("Password must be at least 6 characters long");
        RULES.add("Role must be either ADMIN or USER");
    }

    private final PasswordEncoder passwordEncoder;

    // Constructor injection of PasswordEncoder to encode passwords.
    public UserValidator(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    // This method performs validation on a User entity.
    public void validate(User user) throws InvalidEntityException {
        List<String> violations = new ArrayList<>();

        validateUserId(user.getUserId(), violations);
        validateUsername(user.getUsername(), violations);
        validateEmail(user.getEmail(), violations);
        validatePassword(user.getPassword(), violations);
        validateRole(user.getRole(), violations);

        if (!violations.isEmpty()) {
            throw new InvalidEntityException("User", "User validation failed", createValidationData(violations));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void validateUserId(Long userId, List<String> violations) {
        if (userId != null && userId < 0) {
            violations.add(RULES.get(0));
        }
    }


    private void validateUsername(String username, List<String> violations) {
        if (username == null || username.length() < 4 || !Pattern.matches("^[a-zA-Z ]*$", username)) {
            violations.add(RULES.get(2));
        }
    }

    private void validateEmail(String email, List<String> violations) {
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            violations.add(RULES.get(3));
        }
    }

    private void validatePassword(String password, List<String> violations) {
        if (password == null || password.length() < 6) {
            violations.add(RULES.get(4));
        }
    }

    private void validateRole(Role role, List<String> violations) {
        if (role == null || (!role.getRoleName().equals("ADMIN") && !role.getRoleName().equals("USER"))) {
            violations.add(RULES.get(5));
        }
    }

    // Create a map containing validation data for reporting.
    private String createValidationData(List<String> violations) {
        StringBuilder problems = new StringBuilder();
        if(violations.isEmpty()) return problems.toString();
        for(String v: violations) problems.append(v).append(", ");
        return problems.substring(0, problems.toString().length() - 2);
    }
}
