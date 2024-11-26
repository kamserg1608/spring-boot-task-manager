package org.example.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.constants.TasksConstants;
import org.example.taskmanager.dto.ResponseDto;
import org.example.taskmanager.dto.TaskDto;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.service.kafka.DispatchService;
import org.example.taskmanager.service.task.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskRestController {

    private final DispatchService dispatchService;
    private final TaskServiceImpl taskServiceImpl;


    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto task) {
        TaskDto created = taskServiceImpl.createTask(task);
        dispatchService.send(task.getId(), TaskStatus.CREATED);
        return created;
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskServiceImpl.getTaskById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto task) {
        return taskServiceImpl.updateTaskById(id, task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteTask(@PathVariable Long id) {
        boolean isDeleted = taskServiceImpl.deleteTask(id);
        if (isDeleted) {
            dispatchService.send(id, TaskStatus.DELETED);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(TasksConstants.STATUS_200, TasksConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(TasksConstants.STATUS_417, TasksConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskServiceImpl.getAllTasks();
    }

}