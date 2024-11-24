package org.example.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.annotations.HowManyRecords;
import org.example.taskmanager.exception.ResourceNotFoundException;
import org.example.taskmanager.mappers.TaskMapper;
import org.example.taskmanager.model.TaskDTO;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDTO createTask(TaskDTO task) {
        return taskMapper.taskToTaskDto(taskRepository.save(taskMapper.taskDtoToTask(task)));
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        checkTaskIdExist(id);
        return Optional.ofNullable(taskMapper.taskToTaskDto(taskRepository.findById(id)
                .orElse(null)));
    }

    public Optional<TaskDTO> updateTaskById(Long id, TaskDTO updatedTaskDTO) {
        AtomicReference<Optional<TaskDTO>> atomicReference = new AtomicReference<>();

        taskRepository.findById(id).ifPresentOrElse(foundTask -> {
            foundTask.setDescription(updatedTaskDTO.getDescription());
            foundTask.setTitle(updatedTaskDTO.getTitle());
            foundTask.setUserId(updatedTaskDTO.getUserId());
            foundTask.setDescription(updatedTaskDTO.getDescription());
            atomicReference.set(Optional.of(taskMapper
                    .taskToTaskDto(taskRepository.save(foundTask))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    public void deleteTask(Long id) {
        checkTaskIdExist(id);
        taskRepository.deleteById(id);
    }

    @HowManyRecords
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::taskToTaskDto)
                .collect(Collectors.toList());
    }

    public void checkTaskIdExist(Long id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }
}
