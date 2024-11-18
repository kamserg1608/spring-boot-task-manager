package org.example.taskmanager.mappers;

import org.example.taskmanager.entity.Task;
import org.example.taskmanager.model.TaskDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {
    Task taskDtoToTask(TaskDTO dto);

    TaskDTO taskToTaskDto(Task task);
}
