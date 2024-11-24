package org.example.taskmanager.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaClientProducer {

    private final KafkaTemplate<String, Object> kafkaProducer;


    @Value("${kafka.topic.task_notification_registration}")
    private String topic;

    public void send(Object o) {
        try {
            kafkaProducer.send(topic, o).get();
            kafkaProducer.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
