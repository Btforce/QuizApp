package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private TextView textViewScore;
    private Button buttonReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent lastIntent = getIntent();

        int score = lastIntent.getIntExtra(MainActivity.EXTRA_SCORE, 0);

        wireWidgets();
        setListeners();

        textViewScore.setText(getString(R.string.Score)+ ": " + score);
    }

    private void setListeners() {
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


    private void wireWidgets() {
        textViewScore = findViewById(R.id.textView_score_score);
        buttonReset = findViewById(R.id.button_score_reset);
    }
}
