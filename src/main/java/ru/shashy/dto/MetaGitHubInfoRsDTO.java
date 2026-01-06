package ru.shashy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetaGitHubInfoRsDTO(
        List<AssetRsDTO> assets
) {}