package com.mloprio.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("server2")
public class Server2Controller {

    @GetMapping("/server2")
    public String healthServer2() {
        return "Server 2 is healthy";
    }
}