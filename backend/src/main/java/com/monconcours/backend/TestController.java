package com.monconcours.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "Le Backend fonctionne parfaitement !";
    }
}