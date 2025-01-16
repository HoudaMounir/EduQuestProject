package com.example.EduQuest.repository;

import com.example.EduQuest.model.UserTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTestResultRepository extends JpaRepository<UserTestResult, Long> {
    List<UserTestResult> findByUserId(Long userId);
    List<UserTestResult> findByQuizId(Long quizId);
}