package com.example.wack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);

       TextView textResult = findViewById(R.id.textResult);

       textResult.setText(String.valueOf(GameScreen.SCORE)); // exibe a pontuação como texto

    }


    public void mainMenu(View view){
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}