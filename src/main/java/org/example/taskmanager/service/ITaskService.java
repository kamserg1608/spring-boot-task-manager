package org.example.taskmanager.service;

import org.example.taskmanager.entity.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

    Task createTask(Task task);

    Optional<Task> getTaskById(Long id);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    List<Task> getAllTasks();
}
