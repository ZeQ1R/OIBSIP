package com.example.quizapp;

public class Question {

    private final String question;
    private final String[] answers;
    private final int correctAnswer;

    public Question(
            String question,
            String[] answers,
            int correctAnswer
    ) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}