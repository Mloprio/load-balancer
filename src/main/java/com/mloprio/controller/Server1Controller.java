package com.mloprio.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("server1")
public class Server1Controller {

    @GetMapping("/server1")
    public String healthServer1() {
        return "Server 1 is healthy";
    }
}