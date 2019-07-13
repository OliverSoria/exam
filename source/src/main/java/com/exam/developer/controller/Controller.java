package com.exam.developer.controller;

import com.exam.developer.business.StarterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private StarterImpl starter;

    @GetMapping("/")
    public void controller() {
        starter.run();
    }
}
