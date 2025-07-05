package com.everteh.api.demo.controller;

import com.everteh.api.demo.repository.IdRegistry;
import com.everteh.api.demo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {
    private static final String TOPIC = "testTopic";
    private final KafkaProducerService producer;
    private record IdRequest(String id) {}

    @Autowired
    private IdRegistry registry;

    public MainController(KafkaProducerService producer){
        this.producer = producer;
    }

    @GetMapping("/getId")
    public String getId() {
        return registry.getLastId();
    }

    @PostMapping("/saveId")
    public void saveId(@RequestBody IdRequest request) {
        registry.saveId(request.id);
    }

    @PostMapping("/sendId")
    public ResponseEntity<String> sendId() {
        try {
            String id = registry.getLastId();
            producer.sendMessage(TOPIC, id);
            return ResponseEntity.ok("topic: " + TOPIC + "\nid: " + id);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }

}
