package com.example.trivia_questions_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Game_Activity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private RadioButton radioButton;
    private String userName;
    private int numOfQues;
    private int question_num = 0;
    private int correctAns=0;
    private int score = 0;
    private Question[] ques;
    private Questions questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        userName = this.getIntent().getExtras().getString("user_name");
        numOfQues = this.getIntent().getExtras().getInt("question_num");

        questions = new Questions(numOfQues);

        // init constraintLayout
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);

        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        // setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(5000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);

        try {
            questions.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ques = questions.getQuestions();

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textViewScore.setText("Score: 0");
        textViewQuestionCount.setText("Question: 1/"+numOfQues);

        textViewQuestion.setText(ques[question_num].getTextQuestion());
        rb1.setText(ques[question_num].getAnswer(1));
        rb2.setText(ques[question_num].getAnswer(2));
        rb3.setText(ques[question_num].getAnswer(3));
        rb4.setText(ques[question_num].getAnswer(4));

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int selectedId = rbGroup.getCheckedRadioButtonId();
            if(selectedId == -1){
                Toast.makeText(Game_Activity.this, "You have to choose answer",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                radioButton = findViewById(selectedId);

                if(radioButton.getText().equals(ques[question_num].getCorrectAnswer())){
                    Toast.makeText(Game_Activity.this, "Correct",Toast.LENGTH_SHORT).show();
                    correctAns++;
                    score = score + 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    question_num++;
                }
                else{
                    Toast.makeText(Game_Activity.this, "NOT Correct",Toast.LENGTH_SHORT).show();
                    question_num++;

                }

                if (question_num == numOfQues){
                    gameOver();
                    return;
                }

                nextQuestion(question_num);
            }
            }
        });
    }

    private void nextQuestion(int i) {
            textViewQuestion.setText(ques[question_num].getTextQuestion());
            rb1.setText(ques[question_num].getAnswer(1));
            rb2.setText(ques[question_num].getAnswer(2));
            rb3.setText(ques[question_num].getAnswer(3));
            rb4.setText(ques[question_num].getAnswer(4));
            textViewQuestionCount.setText("Question: " + String.valueOf(i + 1) + "/" + numOfQues);
    }

    private void gameOver() {
        if(correctAns == numOfQues){
            Intent intent = new Intent(this, theGeniusOne.class);
            startActivity(intent);
        } else{
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setMessage(userName+ " your score is: " + String.valueOf(score))
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Game_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).create();
            dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }
}
