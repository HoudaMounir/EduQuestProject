package com.example.EduQuest.service;

import com.example.EduQuest.model.Question;
import com.example.EduQuest.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // Fetch all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizIdWithOptions(quizId);
    }
    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long questionId, Question updatedQuestion) {
        return questionRepository.findById(questionId).map(existingQuestion -> {
            existingQuestion.setText(updatedQuestion.getText());
            existingQuestion.setOptions(updatedQuestion.getOptions());
            existingQuestion.setCorrectAnswerIndex(updatedQuestion.getCorrectAnswerIndex());
            return questionRepository.save(existingQuestion);
        }).orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
    }

    // Delete a question by ID
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }
}