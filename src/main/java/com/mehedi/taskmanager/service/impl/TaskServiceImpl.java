package com.mehedi.taskmanager.service.impl;

import com.mehedi.taskmanager.entity.Task;
import com.mehedi.taskmanager.entity.User;
import com.mehedi.taskmanager.exception.TaskNotFoundException;
import com.mehedi.taskmanager.exception.UserNotFoundException;
import com.mehedi.taskmanager.model.taskdto.TaskCreateDTO;
import com.mehedi.taskmanager.model.taskdto.TaskResponseDTO;
import com.mehedi.taskmanager.model.taskdto.TaskUpdateDTO;
import com.mehedi.taskmanager.repository.TaskRepository;
import com.mehedi.taskmanager.repository.UserRepository;
import com.mehedi.taskmanager.service.TaskService;
import com.mehedi.taskmanager.utilities.enums.TaskStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskResponseDTO createTask(TaskCreateDTO taskCreateDTO, String username) {
        Task task = modelMapper.map(taskCreateDTO, Task.class);
        task.setStatus(TaskStatus.TODO);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found", "Task creation", "User not found for username: " + username));
        task.setAssignedUser(user);

        Task createdTask = taskRepository.save(task);
        return modelMapper.map(createdTask, TaskResponseDTO.class);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks(String username) {
        List<Task> tasks = taskRepository.findByAssignedUser_Username(username);
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO updateTask(UUID taskId, TaskUpdateDTO taskUpdateDTO, String username) {
        Task existingTask = getTaskByIdAndUsername(taskId, username);

        if (taskUpdateDTO.getStatus() != null) {
            existingTask.setStatus(taskUpdateDTO.getStatus());
        }

        Task updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskResponseDTO.class);
    }

    @Override
    public void deleteTask(UUID taskId, String username) {
        Task existingTask = getTaskByIdAndUsername(taskId, username);

        taskRepository.delete(existingTask);
    }

    private Task getTaskByIdAndUsername(UUID taskId, String username) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty() || !taskOptional.get().getAssignedUser().getUsername().equals(username)) {
            throw new TaskNotFoundException("Task not found or not authorized", "Task operation", "Task not found or not authorized for user: " + username);
        }

        return taskOptional.get();
    }
}