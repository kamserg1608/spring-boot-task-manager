package org.example.taskmanager.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
}
