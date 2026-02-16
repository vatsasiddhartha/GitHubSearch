package com.example.githubRepo.Api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class GitHubClient {

    private final WebClient webClient;

    public GitHubClient(@Value("${github.api.url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Map<String, Object> searchRepositories(String query, String language, String sort) {

        String tempQuery = query;

        if (language != null && !language.isEmpty()) {
            tempQuery += "+language:" + language;
        }

        // make variables final for lambda
        final String searchQueryFinal = tempQuery;
        final String sortFinal = sort;

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", searchQueryFinal)
                        .queryParam("sort", sortFinal)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
