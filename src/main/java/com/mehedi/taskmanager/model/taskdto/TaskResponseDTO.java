package com.mehedi.taskmanager.model.taskdto;

import com.mehedi.taskmanager.utilities.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
    private UUID taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private AssignedUserDTO assignedUser;
}