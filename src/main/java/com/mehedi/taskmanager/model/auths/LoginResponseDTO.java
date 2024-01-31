package com.mehedi.taskmanager.model.auths;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String username;
    private String bearerToken;
    private List<String> roles;
}
