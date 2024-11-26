package org.example.taskmanager.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.kafka.KafkaClientProducer;
import org.example.taskmanager.mappers.MessageMapper;
import org.example.taskmanager.model.TaskStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {
    private final KafkaClientProducer kafkaClientProducer;
    private final MessageMapper messageMapper;

    public void send(Long id, TaskStatus status) {
        kafkaClientProducer.send(messageMapper.toMessageDto(id, status));
    }
}
