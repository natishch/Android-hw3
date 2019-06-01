package com.example.trivia_questions_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class theGeniusOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_genius_one);

        Button btn = (Button) findViewById(R.id.goHome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });
    }

    public void returnHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
