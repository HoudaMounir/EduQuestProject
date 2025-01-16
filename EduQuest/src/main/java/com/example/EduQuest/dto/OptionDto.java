package com.example.EduQuest.dto;

public class OptionDto {
    private String text;
    private boolean isCorrect;

    public OptionDto(){}

    public OptionDto(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}