package com.example.threeandfree;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    int active = 1;
    int[] states = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPositions = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
    boolean gameState = true;

    public void nullImageViews(){
        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView2);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView3);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView4);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView5);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView6);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView7);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView8);
        imageView.setImageDrawable(null);
        imageView = findViewById(R.id.imageView9);
        imageView.setImageDrawable(null);
    }

    public int hasWon(){
        for(int[] x:winningPositions){
            if(states[x[0]] == states[x[1]] && states[x[1]]==states[x[2]] && states[x[0]]!=0)
                return states[x[0]];
        }
        return -1;
    }

    public void animateTopDown(ImageView imageView){
        imageView.setTranslationY(-1500);
        imageView.animate().translationYBy(1500).setDuration(800);
    }

    public void animateFromLeft(ImageView imageView){
        imageView.setTranslationX(-1500);
        imageView.animate().translationXBy(1500).setDuration(800);
    }

    public void animateFromRight(ImageView imageView){
        imageView.setTranslationX(1500);
        imageView.animate().translationXBy(-1500).setDuration(800);
    }

    public void animateBottomUp(ImageView imageView){
        imageView.setTranslationY(1500);
        imageView.animate().translationYBy(-1500).setDuration(800);
    }

    public void Down(View view){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        ImageView imageView = (ImageView) view;
        int clicked = Integer.parseInt(imageView.getTag().toString());
        if(gameState && states[clicked]==0) {

            if (active == 1) {
                imageView.setImageResource(R.drawable.purple);
                states[clicked] = active;
                active = 2;
            }
            else {
                imageView.setImageResource(R.drawable.red);
                states[clicked] = active;
                active = 1;
            }

            //Animation
            Random r = new Random();
            int n = r.nextInt(20) + clicked;
            if(n%4==0)
                animateTopDown(imageView);
            else if(n%4==1)
                animateFromLeft(imageView);
            else if(n%4==2)
                animateFromRight(imageView);
            else
                animateBottomUp(imageView);

            //Check if Won
            int check = hasWon();
            if (check == 1){
                gameState = false;
                Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show();
                mediaPlayer = MediaPlayer.create(this, R.raw.win);
                mediaPlayer.start();
                Button play = findViewById(R.id.button);
                play.setVisibility(View.VISIBLE);
            }
            else if (check == 2){
                gameState = false;
                Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
                mediaPlayer = MediaPlayer.create(this, R.raw.win);
                mediaPlayer.start();
                Button play = findViewById(R.id.button);
                play.setVisibility(View.VISIBLE);
            }

            int i;
            for(i=1; i<10; i++){
                if(states[i] == 0)
                    break;
            }
            if(i==10){
                Button play = findViewById(R.id.button);
                play.setVisibility(View.VISIBLE);
            }
        }
    }

    public void playAgain(View view){
        gameState = true;
        active = 1;
        Button play = findViewById(R.id.button);
        play.setVisibility(View.INVISIBLE);

//        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
//        for(int i=0; i<gridLayout.getChildCount(); i++) {
//            states[i] = 0;
//        }
        nullImageViews();
        for(int i=0; i<10; i++){
            states[i] = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = findViewById(R.id.button);
        play.setVisibility(View.INVISIBLE);
    }
}
