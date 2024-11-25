package org.example.taskmanager.mappers;

import org.example.taskmanager.dto.MessageDto;
import org.example.taskmanager.model.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageDto toMessageDto(Long id, TaskStatus status) {
        return MessageDto.builder()
                .id(id)
                .status(status.toString())
                .build();
    }
}