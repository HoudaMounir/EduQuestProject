package com.example.EduQuest.service;

import com.example.EduQuest.model.UserTestResult;
import com.example.EduQuest.repository.UserTestResultRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserTestResultService {
    private final UserTestResultRepository userTestResultRepository;

    public UserTestResultService(UserTestResultRepository userTestResultRepository) {
        this.userTestResultRepository = userTestResultRepository;
    }

    public List<UserTestResult> getResultsByUser(Long userId) {
        return userTestResultRepository.findByUserId(userId);
    }

    public void deleteTestResult(Long resultId) {
        if (!userTestResultRepository.existsById(resultId)) {
            throw new ResourceNotFoundException("Test result not found");
        }
        userTestResultRepository.deleteById(resultId);
    }
    public UserTestResult saveTestResult(UserTestResult userTestResult) {
        return userTestResultRepository.save(userTestResult);
    }

    public List<UserTestResult> getTestResultsByQuiz(Long quizId) {
        return userTestResultRepository.findByQuizId(quizId);
    }

    public List<UserTestResult> getAllResults() {
        return userTestResultRepository.findAll();
    }

    public Map<String, Object> getAggregatedResults() {
        List<UserTestResult> results = userTestResultRepository.findAll();
        double averageScore = results.stream().mapToDouble(UserTestResult::getScore).average().orElse(0.0);
        long totalUsers = results.stream().map(result -> result.getUser().getId()).distinct().count();
        long totalQuizzes = results.stream().map(result -> result.getQuiz().getId()).distinct().count();

        Map<String, Object> aggregatedData = new HashMap<>();
        aggregatedData.put("averageScore", averageScore);
        aggregatedData.put("totalUsers", totalUsers);
        aggregatedData.put("totalQuizzes", totalQuizzes);
        return aggregatedData;
    }
}