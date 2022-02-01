package com.example.wack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.wack_a_mole.R.*;

public class Ranking extends AppCompatActivity {

    public static int[] ranking2 = new int[3];

    private String[] pontosStr = new String[3];

    private TextView[] pontosTxt = new TextView[3];

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    final DatabaseReference databaseReference = firebaseDatabase.getReference("ranking");

    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_ranking);

        pontosTxt[0] = (TextView) findViewById(R.id.txt1Lugar);
        pontosTxt[1] = (TextView) findViewById(R.id.txt2Lugar);
        pontosTxt[2] = (TextView) findViewById(R.id.txt3Lugar);

        getRanking();

        btnVoltar = findViewById(id.btnVoltar);


    }

    public void onClick(View view) {

        if (view == btnVoltar) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void getRanking(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    ranking2[i] = Integer.parseInt(data.getValue().toString());
                    pontosStr[i] = (i + 1) + "º LUGAR: " + ranking2[i] + " PONTOS";
                    pontosTxt[i].setText(pontosStr[i]);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void setRanking(){

        int lugar1 = ranking2[0];
        int lugar2 = ranking2[1];
        int lugar3 = ranking2[2];

        RankingHelper helper = new RankingHelper(lugar1, lugar2, lugar3);

        databaseReference.setValue(helper);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}