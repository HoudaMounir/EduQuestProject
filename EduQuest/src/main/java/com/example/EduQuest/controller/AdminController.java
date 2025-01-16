package com.example.EduQuest.controller;

import com.example.EduQuest.model.User;
import com.example.EduQuest.model.UserTestResult;
import com.example.EduQuest.service.UserService;
import com.example.EduQuest.service.UserTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserTestResultService userTestResultService;

    @Autowired
    private UserService userService;

    @GetMapping("/results")
    public ResponseEntity<List<UserTestResult>> getAllResults() {
        return ResponseEntity.ok(userTestResultService.getAllResults());
    }

    @GetMapping("/results/aggregated")
    public ResponseEntity<Map<String, Object>> getAggregatedResults() {
        return ResponseEntity.ok(userTestResultService.getAggregatedResults());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
