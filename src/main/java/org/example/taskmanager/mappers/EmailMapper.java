package org.example.taskmanager.mappers;

import org.example.taskmanager.model.EmailDetails;
import org.example.taskmanager.model.MessageDto;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {
    public EmailDetails toEmailDetails(MessageDto messageDto) {
        return EmailDetails.builder()
                .msgBody("Task with id: " + messageDto.getId() + " has been updated to status: " + messageDto.getStatus())
                .build();

    }
}
