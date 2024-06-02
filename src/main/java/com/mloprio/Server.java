package com.mloprio;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
    private String url;
    private boolean isHealthy;

    public Server(String url) {
        this.url = url;
        this.isHealthy = true;
    }

    public void checkHealth() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            isHealthy = (responseCode == 200);
        } catch (IOException e) {
            isHealthy = false;
        }
    }

    public String getUrl() {
        return url;
    }

    public boolean getIsHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean isHealthy) {
        this.isHealthy = isHealthy;
    }
}