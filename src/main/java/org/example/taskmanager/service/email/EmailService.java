package org.example.taskmanager.service.email;

import org.example.taskmanager.model.EmailDetails;
import org.example.taskmanager.model.MessageDto;

import java.util.List;

public interface EmailService {

    void sendSimpleMail(EmailDetails details);

    void createNotification(List<MessageDto> emails);

}
