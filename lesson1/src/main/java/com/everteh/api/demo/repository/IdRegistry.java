package com.everteh.api.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class IdRegistry {
    private static final int BUFFER_SIZE = 100;
    List<String> registry = Collections.synchronizedList(new ArrayList<>(BUFFER_SIZE));

    private final KafkaTemplate<String, String> kafkaTemplate;

    public IdRegistry(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String getLastId() {
        try {
            return registry.getLast();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException();
        }
    }

    public void saveId(String value) {
        registry.add(value);
        if (registry.size() >= BUFFER_SIZE) {
            sendToKafka();
        }
    }

    private void sendToKafka() {
        try {
            registry.forEach(id -> kafkaTemplate.send("testTopic", id));
            registry.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
