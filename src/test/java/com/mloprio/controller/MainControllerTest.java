package com.mloprio.controller;

import com.mloprio.LoadBalancer;
import com.mloprio.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MainControllerTest {

    private LoadBalancer mockLoadBalancer;
    private RestTemplate mockRestTemplate;
    private MainController controller;

    @BeforeEach
    public void setUp() {
        mockLoadBalancer = Mockito.mock(LoadBalancer.class);
        mockRestTemplate = Mockito.mock(RestTemplate.class);
        controller = new MainController(mockLoadBalancer, mockRestTemplate);
    }

    @Test
    public void testGetNextServerWithServer() {
        when(mockLoadBalancer.getNextServer())
                .thenReturn(new Server("http://localhost:8080"));
        when(mockRestTemplate.getForEntity(anyString(), any()))
                .thenReturn(ResponseEntity.ok("Server response"));

        String response = controller.getNextServer();

        assertEquals("Server response", response);
    }

    @Test
    public void testGetNextServerWithNoServer() {
        when(mockLoadBalancer.getNextServer())
                .thenReturn(null);

        String response = controller.getNextServer();

        assertEquals("No healthy server available\n", response);
    }
}