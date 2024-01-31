package com.mehedi.taskmanager.exception;

public class TaskNotFoundException extends CustomException {
    public TaskNotFoundException(String message, String operation, String reason) {
        super("TaskNotFoundException", message, operation, reason);
    }
}