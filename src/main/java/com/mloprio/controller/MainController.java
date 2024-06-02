package com.mloprio.controller;

import com.mloprio.LoadBalancer;
import com.mloprio.Server;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    private final LoadBalancer loadBalancer;
    private final RestTemplate restTemplate;

    public MainController(LoadBalancer loadBalancer, RestTemplate restTemplate) {
        this.loadBalancer = loadBalancer;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String getNextServer() {
        Server server = loadBalancer.getNextServer();

        if (server != null) {
            ResponseEntity<String> response = restTemplate.getForEntity(server.getUrl(), String.class);
            return response.getBody();
        } else {
            return "No healthy server available\n";
        }
    }
}