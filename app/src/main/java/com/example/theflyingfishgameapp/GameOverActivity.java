package com.example.theflyingfishgameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button StartGameAgain;
    private TextView displayScore;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        score = getIntent().getExtras().get("score").toString();
        StartGameAgain=(Button) findViewById(R.id.play_again_btn);
        displayScore =(TextView) findViewById(R.id.displayScore);
        StartGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
        displayScore.setText("Score = " +  score);
    }
}
