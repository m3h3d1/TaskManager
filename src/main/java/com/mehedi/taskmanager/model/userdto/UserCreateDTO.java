package com.mehedi.taskmanager.model.userdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserCreateDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}
