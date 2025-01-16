package com.example.EduQuest.dto;

import com.example.EduQuest.model.Question;
import com.example.EduQuest.model.QuestionOption;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDto {
    private String text;
    private String correct_answer;
    private int correct_answer_index;
    private List<OptionDto> options;

    public QuestionDto(){}

    public QuestionDto(Question question) {
        this.text = question.getText();
        this.correct_answer = question.getCorrectAnswer();
        this.correct_answer_index = question.getCorrectAnswerIndex();
        this.options = question.getOptions().stream()
                .map(option -> new OptionDto(option.getText(), option.isCorrect())) // Map to OptionDto
                .collect(Collectors.toList());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public int getCorrect_answer_index() {
        return correct_answer_index;
    }

    public void setCorrect_answer_index(int correct_answer_index) {
        this.correct_answer_index = correct_answer_index;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }
}