package com.example.githubRepo.services;

import com.example.githubRepo.Api.GitHubClient;
import com.example.githubRepo.Entity.RepositoryEntity;
import com.example.githubRepo.dto.RepositoryResponseDto;
import com.example.githubRepo.dto.SearchRequestDto;
import com.example.githubRepo.repository.RepositoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

    private final GitHubClient gitHubClient;
    private final RepositoryRepository repositoryRepository;

    public GitHubService(GitHubClient gitHubClient,
                         RepositoryRepository repositoryRepository) {
        this.gitHubClient = gitHubClient;
        this.repositoryRepository = repositoryRepository;
    }

    public List<RepositoryResponseDto> searchAndSave(SearchRequestDto request) {

        Map<String, Object> response =
                gitHubClient.searchRepositories(
                        request.getQuery(),
                        request.getLanguage(),
                        request.getSort()
                );

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) response.get("items");

        List<RepositoryResponseDto> result = new ArrayList<>();

        for (Map<String, Object> item : items) {

            Long id = ((Number) item.get("id")).longValue();
            String name = (String) item.get("name");
            String description = (String) item.get("description");

            Map<String, Object> ownerMap =
                    (Map<String, Object>) item.get("owner");

            String owner = (String) ownerMap.get("login");
            String language = (String) item.get("language");

            Integer stars =
                    ((Number) item.get("stargazers_count")).intValue();

            Integer forks =
                    ((Number) item.get("forks_count")).intValue();

            LocalDateTime updated =
                    LocalDateTime.parse(
                            ((String) item.get("updated_at"))
                                    .replace("Z", "")
                    );

            RepositoryEntity entity =
                    new RepositoryEntity(id, name, description,
                            owner, language, stars, forks, updated);

            repositoryRepository.save(entity); // UPSERT

            result.add(new RepositoryResponseDto(
                    id, name, description, owner,
                    language, stars, forks, updated));
        }

        return result;
    }

    public List<RepositoryResponseDto> getStoredRepositories(
            String language, Integer minStars, String sort) {

        List<RepositoryEntity> repos = repositoryRepository.findAll();

        if (language != null)
            repos = repos.stream()
                    .filter(r -> r.getLanguage() != null &&
                            r.getLanguage().equalsIgnoreCase(language))
                    .toList();

        if (minStars != null)
            repos = repos.stream()
                    .filter(r -> r.getStars() >= minStars)
                    .toList();

        Comparator<RepositoryEntity> comparator =
                switch (sort == null ? "stars" : sort) {
                    case "forks" -> Comparator.comparing(RepositoryEntity::getForks);
                    case "updated" -> Comparator.comparing(RepositoryEntity::getLastUpdated);
                    default -> Comparator.comparing(RepositoryEntity::getStars);
                };

        repos = repos.stream().sorted(comparator.reversed()).toList();

        return repos.stream()
                .map(r -> new RepositoryResponseDto(
                        r.getId(), r.getName(), r.getDescription(),
                        r.getOwner(), r.getLanguage(),
                        r.getStars(), r.getForks(), r.getLastUpdated()))
                .toList();
    }
}
