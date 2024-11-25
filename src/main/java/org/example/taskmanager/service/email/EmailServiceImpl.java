package org.example.taskmanager.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.dto.MessageDto;
import org.example.taskmanager.mappers.EmailMapper;
import org.example.taskmanager.model.EmailDetails;
import org.example.taskmanager.model.EmailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaEmailSender;
    private final EmailMapper emailMapper;
    private final EmailSender emailSender;

    public void createNotification(List<MessageDto> messages) {
        log.debug("received messages {}", messages.size());
//        messages.forEach(message -> sendSimpleMail(emailMapper.toEmailDetails(message)));

    }

    public void sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(emailSender.username());
            mailMessage.setTo(emailSender.username());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject("Subject");

            javaEmailSender.send(mailMessage);
            log.info("Mail Sent Successfully...");
        } catch (Exception ex) {
            log.error("Error while Sending Mail", ex);
        }
    }
}
