package org.example.taskmanager.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long userId;
}
