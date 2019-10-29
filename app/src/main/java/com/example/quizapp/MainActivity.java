package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    }

    private void setListeners() {
        buttonTrue.setOnClickListener(this);
        buttonFalse.setOnClickListener(this);
    }

    private void wireWidgets() {
        buttonTrue = findViewById(R.id.button_main_true);
        buttonFalse = findViewById(R.id.button_main_false);
        textViewQuestion = findViewById(R.id.textView_main_quesiton);
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

                quiz.checkAnswer(true);


                if(quiz.hasMoreQuestions() == true){
                    quiz.nextQuestion();

                    textViewQuestion.setText(quiz.getQuestionText());


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

                quiz.checkAnswer(false);

                if(quiz.hasMoreQuestions() == true){
                    quiz.nextQuestion();

                    textViewQuestion.setText(quiz.getQuestionText());
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
        }
    }
}
