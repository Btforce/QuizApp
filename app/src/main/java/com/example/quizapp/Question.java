package com.example.quizapp;

public class Question {

    private String question;
    private Boolean answer;

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer=" + answer +
                '}';
    }

    public Question(){

    }

    public String getQuestion(){
        return question;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}
