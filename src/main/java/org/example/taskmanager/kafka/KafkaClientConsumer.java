package org.example.taskmanager.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.dto.MessageDto;
import org.example.taskmanager.service.email.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaClientConsumer {
    private final EmailService emailService;

    @KafkaListener(id = "${kafka.consumer.group-id}",
            topics = "${kafka.topic.task_notification_registration}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<MessageDto> messageList,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) {

        log.debug("Client consumer: {} new message", messageList.size());
        try {
            emailService.createNotification(messageList);
        } catch (Exception e) {
            log.error("Unexpected error processing messages: {}.  Topic: {}, Key: {}", messageList, topic, key, e);
        } finally {
            ack.acknowledge();
        }
        log.debug("Client consumer: completed");
    }
}