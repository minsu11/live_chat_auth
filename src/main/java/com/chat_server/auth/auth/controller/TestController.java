package com.chat_server.auth.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

    @GetMapping("/minsu/chat")
    public String test() {
        return "test";
    }
}
