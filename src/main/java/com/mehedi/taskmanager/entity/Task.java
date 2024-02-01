package com.mehedi.taskmanager.entity;

import com.mehedi.taskmanager.utilities.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "task_id", updatable = false, nullable = false)
    private UUID taskId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;
}
