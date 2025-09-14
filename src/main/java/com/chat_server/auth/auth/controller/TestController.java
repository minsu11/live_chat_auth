package com.chat_server.auth.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/minsu/chat")
    public String test() {
        log.warn("test 들어옴");
        return "test";
    }
}
