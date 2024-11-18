package org.example.taskmanager.service;

import org.example.taskmanager.model.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

    TaskDTO createTask(TaskDTO task);

    Optional<TaskDTO> getTaskById(Long id);

    Optional<TaskDTO> updateTaskById(Long id, TaskDTO updatedTask);

    void deleteTask(Long id);

    List<TaskDTO> getAllTasks();
}
