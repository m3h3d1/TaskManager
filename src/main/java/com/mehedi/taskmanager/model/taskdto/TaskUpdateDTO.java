package com.mehedi.taskmanager.model.taskdto;

import com.mehedi.taskmanager.utilities.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDTO {
    private TaskStatus status;
}