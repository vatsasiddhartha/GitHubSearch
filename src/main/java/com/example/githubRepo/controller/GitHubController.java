package com.example.githubRepo.controller;

import com.example.githubRepo.dto.RepositoryResponseDto;
import com.example.githubRepo.dto.SearchRequestDto;
import com.example.githubRepo.services.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchRepositories(
            @RequestBody SearchRequestDto request) {

        List<RepositoryResponseDto> repos =
                gitHubService.searchAndSave(request);

        return ResponseEntity.ok(Map.of(
                "message", "Repositories fetched and saved successfully",
                "repositories", repos
        ));
    }

    @GetMapping("/repositories")
    public List<RepositoryResponseDto> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStars,
            @RequestParam(required = false) String sort) {

        return gitHubService.getStoredRepositories(language, minStars, sort);
    }
}
