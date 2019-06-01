package com.example.trivia_questions_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class name_and_number extends AppCompatActivity {

    private String userName;
    private int numOfQuestions;

    private EditText nameInput;
    private EditText numberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_and_number);

        nameInput = (EditText) findViewById(R.id.user_name);
        numberInput = (EditText) findViewById(R.id.num_ques);

        Button btn = (Button) findViewById(R.id.startQuiz);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

    }

    public void startQuiz(){
        userName = nameInput.getText().toString();
        numOfQuestions = Integer.valueOf(numberInput.getText().toString());

        Intent intent = new Intent(this, Game_Activity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("question_num", numOfQuestions);
        startActivity(intent);
    }
}
