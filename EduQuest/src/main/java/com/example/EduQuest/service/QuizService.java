package com.example.EduQuest.service;

import com.example.EduQuest.dto.OptionDto;
import com.example.EduQuest.dto.QuestionDto;
import com.example.EduQuest.dto.QuizDto;
import com.example.EduQuest.model.Question;
import com.example.EduQuest.model.QuestionOption;
import com.example.EduQuest.model.Quiz;
import com.example.EduQuest.repository.QuestionRepository;
import com.example.EduQuest.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;


    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public Quiz createQuiz(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setFormat(quizDto.getFormat());
        quiz.setQuestions(new HashSet<>());

        // Generate a unique code for the quiz
        String uniqueCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        quiz.setCode(uniqueCode);

        for (QuestionDto questionDto : quizDto.getQuestions()) {
            Question question = new Question();
            question.setText(questionDto.getText());
            question.setCorrectAnswer(questionDto.getCorrect_answer());
            question.setCorrectAnswerIndex(questionDto.getCorrect_answer_index());
            question.setQuiz(quiz);
            question.setOptions(new HashSet<>());

            for (OptionDto optionDto : questionDto.getOptions()) {
                QuestionOption option = new QuestionOption();
                option.setText(optionDto.getText());
                option.setCorrect(optionDto.isCorrect());
                option.setQuestion(question);

                question.getOptions().add(option);
            }

            quiz.getQuestions().add(question);
        }

        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long quizId, Quiz quiz) {
        Quiz existingQuiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        existingQuiz.setTitle(quiz.getTitle());
        existingQuiz.setDescription(quiz.getDescription());
        return quizRepository.save(existingQuiz);
    }

    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new ResourceNotFoundException("Quiz not found");
        }
        quizRepository.deleteById(quizId);
    }

    public Quiz getQuizByCode(String code) {
        return quizRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Quiz with code " + code + " not found"));
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Question> getQuizQuestions(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public List<Question> generateRandomQuiz(Long quizId, int numberOfQuestions) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(numberOfQuestions, questions.size()));
    }

    public List<Quiz> getQuizzesByCategory(Long categoryId) {
        return quizRepository.findQuizzesByCategoryWithQuestionsAndOptions(categoryId);
    }

    //public List<Quiz> getQuizzesByCategory(Long categoryId) {
        //return quizRepository.findByCategoryId(categoryId);
   // }

    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

}