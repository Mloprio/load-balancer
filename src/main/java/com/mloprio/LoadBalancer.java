package com.mloprio;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {
    private List<Server> servers;
    private AtomicInteger currentServerIndex;

    public LoadBalancer(List<Server> servers) {
        this.servers = servers;
        this.currentServerIndex = new AtomicInteger(0);
    }

    public Server getNextServer() {
        if (servers.isEmpty()) {
            return null;
        }

        int startIndex = currentServerIndex.getAndIncrement() % servers.size();
        int currentIndex = startIndex;

        do {
            Server server = servers.get(currentIndex);
            if (server.getIsHealthy()) {
                return server;
            }

            currentIndex = (currentIndex + 1) % servers.size();
        } while (currentIndex != startIndex);

        return null;
    }

    public List<Server> getServers() {
        return servers;
    }
}