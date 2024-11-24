package org.example.taskmanager.rest;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.model.TaskDTO;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.service.kafka.DispatchService;
import org.example.taskmanager.service.TaskServiceImpl;
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
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task) {
        TaskDTO created = taskServiceImpl.createTask(task);
        dispatchService.send(created.getId(), TaskStatus.CREATED);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskServiceImpl.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO task) {
        return taskServiceImpl.updateTaskById(id, task)
                .map(updatedTask -> {
                    dispatchService.send(updatedTask.getId(), TaskStatus.UPDATED);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskServiceImpl.deleteTask(id);
        dispatchService.send(id, TaskStatus.DELETED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskServiceImpl.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

}