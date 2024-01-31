package com.mehedi.taskmanager.controller;

import com.mehedi.taskmanager.entity.Task;
import com.mehedi.taskmanager.model.taskdto.TaskCreateDTO;
import com.mehedi.taskmanager.model.taskdto.TaskResponseDTO;
import com.mehedi.taskmanager.model.taskdto.TaskUpdateDTO;
import com.mehedi.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskCreateDTO taskCreateDTO, Authentication authentication) {
        String username = authentication.getName();
        TaskResponseDTO createdTask = taskService.createTask(taskCreateDTO, username);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(Authentication authentication) {
        String username = authentication.getName();
        List<TaskResponseDTO> tasks = taskService.getAllTasks(username);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable UUID id, @RequestBody TaskUpdateDTO taskUpdateDTO,
                                                      Authentication authentication) {
        String username = authentication.getName();
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskUpdateDTO, username);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id, Authentication authentication) {
        String username = authentication.getName();
        taskService.deleteTask(id, username);
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
    }
}