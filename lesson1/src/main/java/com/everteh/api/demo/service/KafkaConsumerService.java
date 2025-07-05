package com.everteh.api.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "testTopic", groupId = "demo-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Message received: " + record.value());
    }
}