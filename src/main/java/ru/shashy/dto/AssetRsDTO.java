package ru.shashy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssetRsDTO(

        @JsonProperty("browser_download_url")
        String browserDownloadUrl

) {}