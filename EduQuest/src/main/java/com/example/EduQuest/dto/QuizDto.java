package com.example.EduQuest.dto;

import com.example.EduQuest.model.Quiz;

import java.util.List;
import java.util.stream.Collectors;

public class QuizDto {
    private String title;
    private String description;
    private String format;
    private List<QuestionDto> questions;

    public QuizDto(){}

    public QuizDto(Quiz quiz) {
        this.title = quiz.getTitle();
        this.description = quiz.getDescription();
        this.format = quiz.getFormat();
        this.questions = quiz.getQuestions().stream()
                .map(QuestionDto::new)
                .collect(Collectors.toList());
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}