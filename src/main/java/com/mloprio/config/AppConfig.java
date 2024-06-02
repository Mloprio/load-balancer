package com.mloprio.config;

import com.mloprio.LoadBalancer;
import com.mloprio.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    @Bean
    public LoadBalancer loadBalancer() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("http://localhost:8080/server1"));
        servers.add(new Server("http://localhost:8081/server2"));

        LoadBalancer loadBalancer = new LoadBalancer(servers);

        // Check every 10 seconds the health of the servers
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            for (Server server : servers) {
                server.checkHealth();
            }
        }, 0, 10, TimeUnit.SECONDS);

        return loadBalancer;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}