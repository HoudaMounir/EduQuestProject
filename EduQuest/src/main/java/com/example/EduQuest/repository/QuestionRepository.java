package com.example.EduQuest.repository;

import com.example.EduQuest.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.options WHERE q.quiz.id = :quizId")
    List<Question> findByQuizIdWithOptions(@Param("quizId") Long quizId);
    List<Question> findByQuizId(Long quizId);
}