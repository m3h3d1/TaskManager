package com.mehedi.taskmanager.service;

import com.mehedi.taskmanager.entity.Task;
import com.mehedi.taskmanager.model.taskdto.TaskCreateDTO;
import com.mehedi.taskmanager.model.taskdto.TaskResponseDTO;
import com.mehedi.taskmanager.model.taskdto.TaskUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponseDTO createTask(TaskCreateDTO taskCreateDTO, String username);
    List<TaskResponseDTO> getAllTasks(String username);
    TaskResponseDTO updateTask(UUID taskId, TaskUpdateDTO taskUpdateDTO, String username);
    void deleteTask(UUID taskId, String username);
}