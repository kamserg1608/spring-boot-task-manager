package org.example.taskmanager.service.kafka;


import org.example.taskmanager.model.TaskStatus;

public interface DispatchService {
    void send(Long id, TaskStatus status);

}