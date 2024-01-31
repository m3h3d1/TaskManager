package com.mehedi.taskmanager.service.impl;

import com.mehedi.taskmanager.entity.Role;
import com.mehedi.taskmanager.entity.User;
import com.mehedi.taskmanager.exception.CustomException;
import com.mehedi.taskmanager.exception.InvalidEntityException;
import com.mehedi.taskmanager.exception.ModelMappingException;
import com.mehedi.taskmanager.exception.UserNotFoundException;
import com.mehedi.taskmanager.model.userdto.UserCreateDTO;
import com.mehedi.taskmanager.model.userdto.UserReadDTO;
import com.mehedi.taskmanager.repository.UserRepository;
import com.mehedi.taskmanager.service.interfaces.UserService;
import com.mehedi.taskmanager.validation.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @Override
    public void create(UserCreateDTO userCreateDTO) throws ModelMappingException, InvalidEntityException {
        Optional<User> userOptional = userRepository.findByUsername(userCreateDTO.getUsername());
        if (userOptional.isPresent()) {
            throw new CustomException("DuplicateEntityException", "Can not create a new account",
                    "Creating a new user", "Username: " + userCreateDTO.getUsername() + " is already registered");
        }
        User user = modelMapper.map(userCreateDTO, User.class);
        user.setRole(new Role(userCreateDTO.getRole()));
        userValidator.validate(user);
        userRepository.save(user);
    }

    @Override
    public User getUserEntityByUsername(String username) throws ModelMappingException, UserNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with username: " + username + " not found.", "Searching for user by username",
                    "There is no user in the database with username " + username);
        }
        return userOptional.get();
    }

    @Override
    public UserReadDTO readByUsername(String username) throws ModelMappingException, UserNotFoundException {
        return modelMapper.map(getUserEntityByUsername(username), UserReadDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserEntityByUsername(username);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true,
                roles);
    }
}
