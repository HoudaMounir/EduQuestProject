package com.example.EduQuest.service;

import com.example.EduQuest.model.Question;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.stereotype.Service;

@Service
public class LLMService {
    private final OpenAiService openAiService;

    public LLMService() {
        this.openAiService = new OpenAiService("your-api-key");
    }

    public Question generateQuestion(String topic) {
        // Prompt to generate a multiple-choice question
        CompletionRequest request = CompletionRequest.builder()
                .prompt("Generate a multiple-choice question about: " + topic +
                        ". Provide 4 options labeled A, B, C, D, and specify the correct answer.")
                .maxTokens(300)
                .temperature(0.7)
                .build();

        String response = openAiService.createCompletion(request)
                .getChoices().get(0).getText();

        // Process the response and create a Question object
        return parseQuestionResponse(response);
    }

    private Question parseQuestionResponse(String response) {
        // Parse the LLM response and map it to a Question entity
        // Example response parsing (adjust logic based on response format):
        String[] lines = response.split("\n");
        String questionText = lines[0];
        String optionA = lines[1].substring(3).trim(); // Assuming "A. <option>"
        String optionB = lines[2].substring(3).trim();
        String optionC = lines[3].substring(3).trim();
        String optionD = lines[4].substring(3).trim();
        String correctOption = lines[5].split(":")[1].trim(); // Assuming "Correct Answer: B"

        Question question = new Question();
        question.setText(questionText);

        question.setCorrectAnswer(correctOption);

        return question;
    }
}
