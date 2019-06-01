package com.example.trivia_questions_game;

public class Question {

    private String   question;
    private String   correctAnswer;
    private String[] answers;

    public Question() { }

    public Question(String Question, String[] answers, String correctAnswer) {
        this.question      = Question;
        this.answers       = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getTextQuestion() {
        return question;
    }

    public String getAnswer(int i) {
        return answers[i-1];
    }


    public String getCorrectAnswer(){
        return correctAnswer;
    }
}



