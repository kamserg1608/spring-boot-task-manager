package org.example.taskmanager.service.task;

import org.example.taskmanager.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskDto createTask(TaskDto task);

    TaskDto getTaskById(Long id);

    Optional<TaskDto> updateTaskById(Long id, TaskDto updatedTask);

    boolean  deleteTask(Long id);

    List<TaskDto> getAllTasks();
}
