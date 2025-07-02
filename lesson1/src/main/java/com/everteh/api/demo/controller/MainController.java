package com.everteh.api.demo.controller;

import com.everteh.api.demo.repository.IdRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    private record IdRequest(String id) {}

    @Autowired
    private IdRegistry registry;

    @GetMapping("/getId")
    public String getNum() {
        return registry.getLastId();
    }

    @PostMapping("/saveId")
    public void saveId(@RequestBody IdRequest request) {
        registry.saveId(request.id);
    }

}
