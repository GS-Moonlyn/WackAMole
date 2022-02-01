package com.example.wack_a_mole;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameScreen extends AppCompatActivity {


    private final int frames = 4;
    private int currentFrame;
    private int rng;
    private int lastRNG;
    private Random random = new Random();
    public static int SCORE;
    public static int getPontos() {
        return SCORE;
    }
    private List <ImageView> moles = new ArrayList<>();
    private TextView textScore;
    private final String TEXT_SCORE_BASE = "Score: ";
    private CountDownTimer timer;
    private final int MOLE_TIMER = 550; // tempo de aparecimento entre uma toupeira e outra
    private final int GAME_TIMER = 30000; // tempo até o jogo acabar
    private Context context;
    private MediaPlayer mediaPlayer; // variável de música
    private AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .build();

    //variável de som
    private SoundPool soundPool = new SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(5)
            .build();
    private int id;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_screen);

        this.context = this;
        SCORE = 0;
        textScore = findViewById(R.id.textScore);
        moles.add(findViewById(R.id.mole0));
        moles.add(findViewById(R.id.mole1));
        moles.add(findViewById(R.id.mole2));
        moles.add(findViewById(R.id.mole3));
        moles.add(findViewById(R.id.mole4));
        moles.add(findViewById(R.id.mole5));
        mediaPlayer = MediaPlayer.create(context, R.raw.bgm);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        id = soundPool.load(context, R.raw.hit, 0);




        new CountDownTimer(GAME_TIMER, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                Intent intent = new Intent(context, ResultScreen.class);
                startActivity(intent);
                finish();

            }

        }.start();


    }



    @Override
    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update();
                new Handler().postDelayed(this, 30);
            }
        }, 30);
    }


    public void update() {
        currentFrame = (currentFrame + 1) % frames;
        textScore.setText(TEXT_SCORE_BASE + SCORE);

        // lógica de spawn das toupeiras
        if(!isMoleUp()){
            while (rng == lastRNG){ // sorteia um número enquanto esse número for igual ao último sorteado
              rng = random.nextInt(moles.size());
            }

            lastRNG = rng; // lastRNG guarda o último número sorteado
            moles.get(rng).setVisibility(View.VISIBLE); // instancia a toupeira na posição sorteada

           // remove a toupeira da tela depois de um tempo
           timer = new CountDownTimer(MOLE_TIMER, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    moles.get(rng).setVisibility(View.INVISIBLE);
                }

            };
           timer.start();
        }


    }

    // verifica se a toupeira está na tela
    private boolean isMoleUp(){
        for(ImageView mole : moles){
            if(View.VISIBLE == mole.getVisibility()){
                return true;
            }


        }
        return false;
    }

    public void moleHit(View view){
        SCORE++;
        view.setVisibility(View.INVISIBLE);
        timer.cancel();
        soundPool.play(id, 1, 1, 0, 0, 1);
    }

}
