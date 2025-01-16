package com.example.EduQuest.service;

import com.example.EduQuest.model.User;
import com.example.EduQuest.model.UserTestResult;
import com.example.EduQuest.repository.UserRepository;
import com.example.EduQuest.repository.UserTestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTestResultRepository userTestResultRepository;

    public UserService(UserRepository userRepository, UserTestResultRepository userTestResultRepository) {
        this.userRepository = userRepository;
        this.userTestResultRepository = userTestResultRepository;
    }

    //public User getUserByUsername(String username) {
        //return userRepository.findByUsername(username);
    //}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserTestResult> getUserTestHistory(Long userId) {
        return userTestResultRepository.findByUserId(userId);
    }
}
