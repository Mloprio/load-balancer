package com.mloprio.config;

import com.mloprio.LoadBalancer;
import com.mloprio.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class AppConfigTest {

    @Autowired
    private LoadBalancer loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testLoadBalancerBean() {
        assertNotNull(loadBalancer);

        List<Server> servers = loadBalancer.getServers();

        assertNotNull(servers);
        assertEquals(2, servers.size());
        assertEquals("http://localhost:8080/server1", servers.get(0).getUrl());
        assertEquals("http://localhost:8081/server2", servers.get(1).getUrl());
    }

    @Test
    public void testRestTemplateBean() {
        assertNotNull(restTemplate);
    }
}