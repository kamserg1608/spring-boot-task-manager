package org.example.taskmanager.service.task;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.annotations.HowManyRecords;
import org.example.taskmanager.dto.TaskDto;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.exception.ResourceNotFoundException;
import org.example.taskmanager.exception.TaskAlreadyExistsException;
import org.example.taskmanager.mappers.TaskMapper;
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
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDto createTask(TaskDto task) {
        if (taskRepository.findById(task.getId()).isPresent()) {
            throw new TaskAlreadyExistsException("Task already exists with id " + task.getId());
        }
        return taskMapper.taskToTaskDto(taskRepository.save(taskMapper.taskDtoToTask(task)));
    }

    public TaskDto getTaskById(Long id) {
        return taskMapper.taskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)));
    }

    public Optional<TaskDto> updateTaskById(Long id, TaskDto updatedTaskDto) {
        checkTaskIdExist(id);
        AtomicReference<Optional<TaskDto>> atomicReference = new AtomicReference<>();

        taskRepository.findById(id).ifPresent(foundTask -> {
            foundTask.setDescription(updatedTaskDto.getDescription());
            foundTask.setTitle(updatedTaskDto.getTitle());
            foundTask.setUserId(updatedTaskDto.getUserId());
            foundTask.setDescription(updatedTaskDto.getDescription());
            atomicReference.set(Optional.of(taskMapper
                    .taskToTaskDto(taskRepository.save(foundTask))));
        });

        return atomicReference.get();
    }

    public boolean deleteTask(Long id) {
        checkTaskIdExist(id);
        taskRepository.deleteById(id);
        return true;
    }

    @HowManyRecords
    public List<TaskDto> getAllTasks() {
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
