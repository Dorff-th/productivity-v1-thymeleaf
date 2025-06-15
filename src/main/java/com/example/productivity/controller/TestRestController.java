package com.example.productivity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @GetMapping("/test")
    public String test() {
        return "hello world in ChatGPT!";
    }

    @GetMapping("/trigger-error")
    public String triggerError() {
        throw new RuntimeException("강제 500 에러 발생!");
    }


}
