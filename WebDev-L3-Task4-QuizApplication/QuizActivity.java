package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionCounter;
    private TextView questionText;
    private TextView feedbackText;

    private RadioGroup answersGroup;

    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;

    private Button nextButton;

    private List<Question> questions;

    private int currentQuestion = 0;
    private int score = 0;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        questionCounter =
                findViewById(R.id.questionCounter);

        questionText =
                findViewById(R.id.questionText);

        feedbackText =
                findViewById(R.id.feedbackText);

        answersGroup =
                findViewById(R.id.answersGroup);

        answer1 =
                findViewById(R.id.answer1);

        answer2 =
                findViewById(R.id.answer2);

        answer3 =
                findViewById(R.id.answer3);

        answer4 =
                findViewById(R.id.answer4);

        nextButton =
                findViewById(R.id.nextButton);

        createQuestions();

        Collections.shuffle(questions);

        showQuestion();

        answersGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {

                    if (!answered && checkedId != -1) {
                        checkAnswer();
                    }
                }
        );

        nextButton.setOnClickListener(
                view -> nextQuestion()
        );
    }

    private void createQuestions() {

        questions = new ArrayList<>();

        questions.add(
                new Question(
                        "What is the capital of France?",
                        new String[]{
                                "Paris",
                                "Madrid",
                                "Rome",
                                "Berlin"
                        },
                        0
                )
        );

        questions.add(
                new Question(
                        "Which planet is known as the Red Planet?",
                        new String[]{
                                "Earth",
                                "Mars",
                                "Jupiter",
                                "Venus"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "How many continents are there?",
                        new String[]{
                                "5",
                                "6",
                                "7",
                                "8"
                        },
                        2
                )
        );

        questions.add(
                new Question(
                        "Which ocean is the largest?",
                        new String[]{
                                "Atlantic Ocean",
                                "Indian Ocean",
                                "Arctic Ocean",
                                "Pacific Ocean"
                        },
                        3
                )
        );

        questions.add(
                new Question(
                        "What is the chemical symbol for gold?",
                        new String[]{
                                "Ag",
                                "Au",
                                "Fe",
                                "Cu"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "Who painted the Mona Lisa?",
                        new String[]{
                                "Leonardo da Vinci",
                                "Pablo Picasso",
                                "Vincent van Gogh",
                                "Michelangelo"
                        },
                        0
                )
        );

        questions.add(
                new Question(
                        "What is the largest mammal?",
                        new String[]{
                                "Elephant",
                                "Giraffe",
                                "Blue Whale",
                                "Hippopotamus"
                        },
                        2
                )
        );

        questions.add(
                new Question(
                        "How many sides does a hexagon have?",
                        new String[]{
                                "5",
                                "6",
                                "7",
                                "8"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "Which language is primarily used to style web pages?",
                        new String[]{
                                "HTML",
                                "Python",
                                "CSS",
                                "Java"
                        },
                        2
                )
        );

        questions.add(
                new Question(
                        "What is the boiling point of water at sea level?",
                        new String[]{
                                "50°C",
                                "75°C",
                                "90°C",
                                "100°C"
                        },
                        3
                )
        );
    }

    private void showQuestion() {

        answered = false;

        Question question =
                questions.get(currentQuestion);

        questionCounter.setText(
                "Question " +
                        (currentQuestion + 1) +
                        " of " +
                        questions.size()
        );

        questionText.setText(
                question.getQuestion()
        );

        answer1.setText(
                question.getAnswers()[0]
        );

        answer2.setText(
                question.getAnswers()[1]
        );

        answer3.setText(
                question.getAnswers()[2]
        );

        answer4.setText(
                question.getAnswers()[3]
        );

        resetAnswerColors();

        answersGroup.clearCheck();

        feedbackText.setText("");

        if (currentQuestion ==
                questions.size() - 1) {

            nextButton.setText("Finish");
        } else {

            nextButton.setText("Next");
        }
    }

    private void checkAnswer() {

        int selectedId =
                answersGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            return;
        }

        RadioButton selected =
                findViewById(selectedId);

        int selectedIndex = -1;

        if (selectedId == R.id.answer1) {
            selectedIndex = 0;
        } else if (selectedId == R.id.answer2) {
            selectedIndex = 1;
        } else if (selectedId == R.id.answer3) {
            selectedIndex = 2;
        } else if (selectedId == R.id.answer4) {
            selectedIndex = 3;
        }

        Question question =
                questions.get(currentQuestion);

        if (selectedIndex ==
                question.getCorrectAnswer()) {

            score++;

            selected.setBackgroundColor(
                    Color.rgb(198, 239, 206)
            );

            feedbackText.setText(
                    "Correct!"
            );

            feedbackText.setTextColor(
                    Color.rgb(34, 139, 34)
            );

        } else {

            selected.setBackgroundColor(
                    Color.rgb(255, 199, 206)
            );

            RadioButton correctButton =
                    getRadioButton(
                            question.getCorrectAnswer()
                    );

            correctButton.setBackgroundColor(
                    Color.rgb(198, 239, 206)
            );

            feedbackText.setText(
                    "Incorrect!"
            );

            feedbackText.setTextColor(
                    Color.rgb(200, 0, 0)
            );
        }

        answered = true;
    }

    private RadioButton getRadioButton(
            int index
    ) {

        switch (index) {

            case 0:
                return answer1;

            case 1:
                return answer2;

            case 2:
                return answer3;

            default:
                return answer4;
        }
    }

    private void resetAnswerColors() {

        answer1.setBackgroundColor(
                Color.WHITE
        );

        answer2.setBackgroundColor(
                Color.WHITE
        );

        answer3.setBackgroundColor(
                Color.WHITE
        );

        answer4.setBackgroundColor(
                Color.WHITE
        );
    }

    private void nextQuestion() {

        if (!answered) {

            feedbackText.setText(
                    "Please select an answer."
            );

            return;
        }

        currentQuestion++;

        if (currentQuestion >=
                questions.size()) {

            Intent intent =
                    new Intent(
                            QuizActivity.this,
                            ResultActivity.class
                    );

            intent.putExtra(
                    "score",
                    score
            );

            intent.putExtra(
                    "total",
                    questions.size()
            );

            startActivity(intent);

            finish();

            return;
        }

        showQuestion();
    }
}