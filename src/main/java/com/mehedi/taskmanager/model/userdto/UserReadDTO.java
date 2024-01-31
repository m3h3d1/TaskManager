package com.mehedi.taskmanager.model.userdto;
import com.mehedi.taskmanager.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserReadDTO {
    private Long userId;
    private String username;
    private String email;
    private Role role;
}
