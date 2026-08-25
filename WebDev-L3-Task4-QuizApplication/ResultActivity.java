package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreText;
    private TextView correctText;
    private TextView incorrectText;

    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        scoreText =
                findViewById(R.id.scoreText);

        correctText =
                findViewById(R.id.correctText);

        incorrectText =
                findViewById(R.id.incorrectText);

        restartButton =
                findViewById(R.id.restartButton);

        int score =
                getIntent().getIntExtra(
                        "score",
                        0
                );

        int total =
                getIntent().getIntExtra(
                        "total",
                        10
                );

        int incorrect =
                total - score;

        scoreText.setText(
                "Score: " +
                        score +
                        " / " +
                        total
        );

        correctText.setText(
                "Correct: " +
                        score
        );

        incorrectText.setText(
                "Incorrect: " +
                        incorrect
        );

        restartButton.setOnClickListener(
                view -> {

                    Intent intent =
                            new Intent(
                                    ResultActivity.this,
                                    QuizActivity.class
                            );

                    startActivity(intent);

                    finish();
                }
        );
    }
}