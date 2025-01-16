package com.example.EduQuest.controller;

import com.example.EduQuest.model.UserTestResult;
import com.example.EduQuest.service.UserTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class UserTestResultController {
    @Autowired
    private final UserTestResultService userTestResultService;

    public UserTestResultController(UserTestResultService userTestResultService) {
        this.userTestResultService = userTestResultService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTestResult>> getUserResults(@PathVariable Long userId) {
        return ResponseEntity.ok(userTestResultService.getResultsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<UserTestResult> saveTestResult(@RequestBody UserTestResult testResult) {
        return ResponseEntity.ok(userTestResultService.saveTestResult(testResult));
    }

    @DeleteMapping("/{resultId}")
    public ResponseEntity<String> deleteTestResult(@PathVariable Long resultId) {
        userTestResultService.deleteTestResult(resultId);
        return ResponseEntity.ok("Test result deleted successfully.");
    }
}