package com.example.wack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;
    Button btnRanking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnRanking = findViewById(R.id.btnRanking);

    }



    public void onClick(View view){

        if(view == btnPlay){

            Intent intent = new Intent( this, GameScreen.class);
            startActivity(intent);
            finish();

        } else if(view == btnRanking){

            Intent intent = new Intent( this, Ranking.class);
            startActivity(intent);
            finish();
        }
    }
}