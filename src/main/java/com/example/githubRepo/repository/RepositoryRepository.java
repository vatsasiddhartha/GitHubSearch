package com.example.githubRepo.repository;

import com.example.githubRepo.Entity.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryRepository extends JpaRepository<RepositoryEntity, Long> {

    List<RepositoryEntity> findByLanguageIgnoreCase(String language);

    List<RepositoryEntity> findByStarsGreaterThanEqual(Integer stars);
}
