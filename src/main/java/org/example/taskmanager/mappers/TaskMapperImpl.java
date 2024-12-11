package org.example.taskmanager.mappers;

import org.example.taskmanager.dto.TaskDto;
import org.example.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task taskDtoToTask(TaskDto dto) {
        if (dto == null) {
            return null;
        }

        Task task = Task.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .userId(dto.userId())
                .build();

        return task;
    }

    @Override
    public TaskDto taskToTaskDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDto taskDto = new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getUserId());

        return taskDto;
    }
}
