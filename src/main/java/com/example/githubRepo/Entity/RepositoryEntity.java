package com.example.githubRepo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "repositories")
public class RepositoryEntity {

    @Id
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    private String owner;

    private String language;

    private Integer stars;

    private Integer forks;

    private LocalDateTime lastUpdated;

    public RepositoryEntity() {}

    public RepositoryEntity(Long id, String name, String description,
                            String owner, String language,
                            Integer stars, Integer forks,
                            LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.language = language;
        this.stars = stars;
        this.forks = forks;
        this.lastUpdated = lastUpdated;
    }

    // getters setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public Integer getForks() { return forks; }
    public void setForks(Integer forks) { this.forks = forks; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
