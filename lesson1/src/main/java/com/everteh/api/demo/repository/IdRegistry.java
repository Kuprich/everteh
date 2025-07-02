package com.everteh.api.demo.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class IdRegistry {

    List<String> registry = Collections.synchronizedList(new ArrayList<>());

    public String getLastId() {
        try {
            return registry.getLast();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException();
        }
    }

    public void saveId(String value) {
        registry.add(value);
    }
}
