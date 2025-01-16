package com.example.EduQuest.controller;

import com.example.EduQuest.model.Question;
import com.example.EduQuest.service.LLMService;
import com.example.EduQuest.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private final QuestionService questionService;
    private final LLMService llmService;

    public QuestionController(QuestionService questionService, LLMService llmService) {
        this.questionService = questionService;
        this.llmService = llmService;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuiz(quizId));
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody Question question) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, question));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question deleted successfully.");
    }

    @PostMapping("/generate")
    public ResponseEntity<Question> generateQuestion(@RequestParam String topic) {
        Question generatedQuestion = llmService.generateQuestion(topic);
        questionService.createQuestion(generatedQuestion); // Save to database
        return ResponseEntity.ok(generatedQuestion);
    }
}