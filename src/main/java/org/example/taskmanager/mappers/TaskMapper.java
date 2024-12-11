package org.example.taskmanager.mappers;

import org.example.taskmanager.dto.TaskDto;
import org.example.taskmanager.entity.Task;

public interface TaskMapper {
    Task taskDtoToTask(TaskDto dto);

    TaskDto taskToTaskDto(Task task);
}
