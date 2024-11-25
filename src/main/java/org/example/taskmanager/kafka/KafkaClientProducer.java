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

    public void send(Object message) {
        kafkaProducer.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published event to topic {}: value = {}", topic, message.toString());
                    } else {
                        throw new RuntimeException("Caught an exception", ex);
                    }
                });
    }
}
