package com.mehedi.taskmanager.service;


import com.mehedi.taskmanager.entity.User;
import com.mehedi.taskmanager.exception.InvalidEntityException;
import com.mehedi.taskmanager.exception.ModelMappingException;
import com.mehedi.taskmanager.exception.UserNotFoundException;
import com.mehedi.taskmanager.model.userdto.UserCreateDTO;
import com.mehedi.taskmanager.model.userdto.UserReadDTO;

public interface UserService {
    public void create(UserCreateDTO userCreateDTO) throws ModelMappingException, InvalidEntityException;
    public UserReadDTO readByUsername(String username) throws ModelMappingException, UserNotFoundException;
    public User getUserEntityByUsername(String username) throws ModelMappingException, UserNotFoundException;
}
