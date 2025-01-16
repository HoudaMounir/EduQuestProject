package com.example.EduQuest.service;

import com.example.EduQuest.model.User;
import com.example.EduQuest.model.UserTestResult;
import com.example.EduQuest.repository.UserRepository;
import com.example.EduQuest.repository.UserTestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final UserTestResultRepository userTestResultRepository;

    public AdminService(UserRepository userRepository, UserTestResultRepository userTestResultRepository) {
        this.userRepository = userRepository;
        this.userTestResultRepository = userTestResultRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserTestResult> getAggregatedTestResults() {
        return userTestResultRepository.findAll();
    }
}