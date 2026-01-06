package ru.shashy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;

import java.net.http.HttpClient;
import java.time.Duration;

@Getter
public final class AppConfig {

    @NonNull
    private final ObjectMapper objectMapper;

    @NonNull
    private final HttpClient httpClient;


    public AppConfig() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
}