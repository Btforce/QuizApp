package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_SCORE = "score";
    private Button buttonTrue;
    private Button buttonFalse;
    private TextView textViewQuestion;
    private Gson gson = new Gson();
    private Question[] questions;
    private List<Question> questionList;
    private Quiz quiz;
    private TextView textViewQuestionNumber;
    private ConstraintLayout constraintLayout;



    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream jsonFileInputStream = getResources().openRawResource(R.raw.questions);
        String jsonString = readTextFile(jsonFileInputStream);


        questions = gson.fromJson(jsonString, Question[].class);

        questionList = Arrays.asList(questions);

        quiz = new Quiz(questionList);

        Log.d(TAG, "onCreate: " + jsonString);

        wireWidgets();
        setListeners();
        textViewQuestion.setText(quiz.getQuestionText());
        textViewQuestionNumber.setText(String.valueOf(quiz.getCurrentQuestion()));

    }

    private void setListeners() {
        buttonTrue.setOnClickListener(this);
        buttonFalse.setOnClickListener(this);
    }

    private void wireWidgets() {
        buttonTrue = findViewById(R.id.button_main_true);
        buttonFalse = findViewById(R.id.button_main_false);
        textViewQuestion = findViewById(R.id.textView_main_quesiton);
        textViewQuestionNumber = findViewById(R.id.textView_main_question_number);
        constraintLayout = findViewById(R.id.constraintLayout);
    }

    //reading the text file from
    //https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_main_true: {

                if(quiz.checkAnswer(true)){
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();

                    flashColor(true);
                }
                else{
                    Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();

                    flashColor(false);
                }


                if(quiz.hasMoreQuestions() == true){
                    quiz.nextQuestion();

                    textViewQuestion.setText(quiz.getQuestionText());
                    textViewQuestionNumber.setText(String.valueOf(quiz.getCurrentQuestion()));


                }
                else{

                    int score = quiz.getScore();

                    Intent scoreIntent =
                            new Intent(MainActivity.this, ScoreActivity.class);
                    scoreIntent.putExtra(EXTRA_SCORE, score);

                    startActivity(scoreIntent);
                    quiz.setCurrentQuestion(0);
                    quiz.setScore(0);
                    textViewQuestion.setText(quiz.getQuestionText());

                }

                break;
            }

            case R.id.button_main_false: {

                if(quiz.checkAnswer(false)){
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();

                    flashColor(true);
                }
                else{
                    Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();

                    flashColor(false);
                }

                if(quiz.hasMoreQuestions() == true){
                    quiz.nextQuestion();

                    textViewQuestion.setText(quiz.getQuestionText());
                    textViewQuestionNumber.setText(String.valueOf(quiz.getCurrentQuestion()));
                }
                else{

                    int score = quiz.getScore();

                    Intent scoreIntent =
                            new Intent(MainActivity.this, ScoreActivity.class);
                    scoreIntent.putExtra(EXTRA_SCORE, score);

                    startActivity(scoreIntent);
                    quiz.setCurrentQuestion(0);
                    quiz.setScore(0);
                    textViewQuestion.setText(quiz.getQuestionText());
                    textViewQuestionNumber.setText(String.valueOf(quiz.getCurrentQuestion()));


                }

                break;
            }
        }
    }

    private void flashColor(boolean answer) {
        int red = 255;
        int green = 255;
        int blue = 255;
        final int WHITE = Color.rgb(red,green,blue);
        if(answer){
            int r = 50;
            int g = 205;
            int b = 50;
            int color = Color.rgb(r,g,b);
            constraintLayout.setBackgroundColor(color);
        }
        else{
            int r = 170;
            int g = 34;
            int b = 34;
            int color = Color.rgb(r,g,b);
            constraintLayout.setBackgroundColor(color);
        }

        new CountDownTimer(300,300){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                constraintLayout.setBackgroundColor(WHITE);
            }
        }.start();
    }
}
