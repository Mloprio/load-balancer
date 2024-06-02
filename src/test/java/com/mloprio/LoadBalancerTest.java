package com.mloprio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoadBalancerTest {

    private Server server1;
    private Server server2;
    List<Server> servers;

    @BeforeEach
    public void setUp() {
        server1 = new Server("http://localhost:8080");
        server2 = new Server("http://localhost:8081");
        servers = new ArrayList<>(Arrays.asList(server1, server2));
    }

    @Test
    public void testGetNextServerWithAllHealthyServers() {
        server1.setHealthy(true);
        server2.setHealthy(true);

        LoadBalancer loadBalancer = new LoadBalancer(servers);

        Server firstServer = loadBalancer.getNextServer();
        Server secondServer = loadBalancer.getNextServer();
        Server firstServerAgain = loadBalancer.getNextServer();
        Server secondServerAgain = loadBalancer.getNextServer();

        assertEquals(server1, firstServer);
        assertEquals(server2, secondServer);
        assertEquals(server1, firstServerAgain);
        assertEquals(server2, secondServerAgain);
    }

    @Test
    public void testGetNextServerWithOnlyOneHealthyServer() {
        server1.setHealthy(false);
        server2.setHealthy(true);

        LoadBalancer loadBalancer = new LoadBalancer(servers);

        Server nextServer = loadBalancer.getNextServer();

        assertEquals(server2, nextServer);
    }

    @Test
    public void testGetNextServerWithNoHealthyServers() {
        server1.setHealthy(false);
        server2.setHealthy(false);

        LoadBalancer loadBalancer = new LoadBalancer(servers);

        Server nextServer = loadBalancer.getNextServer();

        assertNull(nextServer);
    }

    @Test
    public void testGetNextServerWithNullServers() {
        servers.clear();
        LoadBalancer loadBalancer = new LoadBalancer(servers);

        Server nextServer = loadBalancer.getNextServer();

        assertNull(nextServer);
    }
}