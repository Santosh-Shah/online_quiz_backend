package com.quizsystem.quizsystem.repository;

import com.quizsystem.quizsystem.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("SELECT DISTINCT q.subject FROM QuestionEntity q")
    List<String> findDistinctSubject();
    Page<QuestionEntity> findBySubject(String subject, Pageable pageable);
}
