package com.example.EduQuest.repository;

import com.example.EduQuest.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questions qu LEFT JOIN FETCH qu.options WHERE q.category.id = :categoryId")
    List<Quiz> findQuizzesByCategoryWithQuestionsAndOptions(@Param("categoryId") Long categoryId);

    List<Quiz> findByCategoryId(Long categoryId);
    Quiz findByTitle(String title);
    Optional<Quiz> findByCode(String code);
}