package ru.shashy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import ru.shashy.dto.AssetRsDTO;
import ru.shashy.dto.MetaGitHubInfoRsDTO;
import ru.shashy.exceptions.NotFoundException;
import ru.shashy.service.DownloadZapretService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class DownloadZapretServiceImpl implements DownloadZapretService {

    private static final String LATEST_RELEASE =
            "https://api.github.com/repos/Flowseal/zapret-discord-youtube/releases/latest";

    private static final Map<String, String> GITHUB_HEADERS = Map.of(
            "Accept", "application/vnd.github+json",
            "User-Agent", "zapret-client-app"
    );

    @NonNull
    private final ObjectMapper objectMapper;

    @NonNull
    private final HttpClient httpClient;

    public DownloadZapretServiceImpl(@NonNull ObjectMapper objectMapper, @NonNull HttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
    }

    @Override
    public Optional<Path> getLatest(Path targetPath) {
        String url = getMetaReleaseInfo().orElseThrow(() -> new NotFoundException("Release is empty"));
        HttpRequest rq = buildRequest(url).GET().build();
        try {
            HttpResponse<Path> rs = httpClient.send(rq, HttpResponse.BodyHandlers.ofFile(targetPath));

            if (rs.statusCode() != 200) {
                throw new IllegalStateException("Download failure: " + rs.statusCode());
            }

            System.out.printf("[INFO] Success download latest. Path: %s%n", targetPath);
            return Optional.of(targetPath);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Download interrupted", e);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<String> getMetaReleaseInfo() {
        try {
            HttpRequest rq = buildRequest(LATEST_RELEASE).GET().build();
            HttpResponse<String> rs = httpClient.send(rq, HttpResponse.BodyHandlers.ofString());

            MetaGitHubInfoRsDTO metaInfo = objectMapper.readValue(rs.body(), MetaGitHubInfoRsDTO.class);
            return metaInfo.assets()
                    .stream()
                    .map(AssetRsDTO::browserDownloadUrl)
                    .filter(s -> s.endsWith(".zip"))
                    .peek(url -> System.out.printf("[INFO] Get latest release: %s%n", url))
                    .findFirst();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Meta request interrupted", e);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    @NonNull
    private HttpRequest.Builder buildRequest(String uri) {
        HttpRequest.Builder rqBuilder = HttpRequest.newBuilder();
        rqBuilder.uri(URI.create(uri));
        GITHUB_HEADERS.forEach(rqBuilder::header);
        return rqBuilder;
    }
}