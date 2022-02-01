package com.example.wack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultScreen extends AppCompatActivity {

    private int pontosUltimaPartida;

    public static int[] ranking = new int[3];

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    final DatabaseReference databaseReference = firebaseDatabase.getReference("ranking");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);

       TextView textResult = findViewById(R.id.textResult);

        pontosUltimaPartida = GameScreen.getPontos();

       textResult.setText(String.valueOf(GameScreen.SCORE)); // exibe a pontuação como texto

        getRanking();

    }

    @Override
    protected void onStop() {
        super.onStop();

        organizarRanking();
        finish();
    }

    private void organizarRanking(){
        if (pontosUltimaPartida > ranking[0]) {
            ranking[2] = ranking[1];
            ranking[1] = ranking[0];
            ranking[0] = pontosUltimaPartida;

        } else if (pontosUltimaPartida > ranking[1]){
            ranking[2] = ranking[1];
            ranking[1] = pontosUltimaPartida;

        } else if (pontosUltimaPartida > ranking[2]){
            ranking[2] = pontosUltimaPartida;
        }
        setRanking();
    }

    public void setRanking(){

        int lugar1 = ranking[0];
        int lugar2 = ranking[1];
        int lugar3 = ranking[2];

        RankingHelper helper = new RankingHelper(lugar1, lugar2, lugar3);

        databaseReference.setValue(helper);
    }

    public void getRanking1(String child){
        databaseReference.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ranking[0] = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    public void getRanking(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ranking[i] = Integer.parseInt(data.getValue().toString());
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    public void mainMenu(View view){
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}