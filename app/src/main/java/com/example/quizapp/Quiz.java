package com.example.quizapp;

import java.util.List;

public class Quiz {
    private int score;
    private List<Question> questionList;
    private Question question;
    private int currentQuestion;
    private boolean answer;

    public Quiz(List questions){
        questionList = questions;
        currentQuestion = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List questionList) {
        this.questionList = questionList;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public boolean getAnswer(){return answer;}

    public void score(){
        score++;
    }


    public boolean checkAnswer(boolean answer){
        if(questionList.get(currentQuestion).getAnswer()== answer){
            return true;
        }
        else{return false;}
    }

    public boolean hasMoreQuestions(){
        if(currentQuestion + 1 <= questionList.size() - 1){
            return true;
        }
        return false;
    }

    public void nextQuestion(){
        currentQuestion++;
    }

    public String getQuestionText(){
        return questionList.get(currentQuestion).getQuestion();
    }


}
