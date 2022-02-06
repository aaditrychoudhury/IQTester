package com.example.iqtester2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView your_score, time_left, ques;
    Button yes_button, no_button;
    boolean isResultRight;
    int a;
    int b;
    int currentResult;
    int userScore =0;
    AlertDialog alertDialog;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        your_score = findViewById(R.id.yourScore);
        time_left = findViewById(R.id.timeLeft);
        ques = findViewById(R.id.question);

        no_button = findViewById(R.id.noButton);
        yes_button = findViewById(R.id.yesButton);

        String score = "Your Score = 0";
        your_score.setText(score);

        createNewQuestion();

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = a+b;
                if(result == currentResult){
                    //user's ans is correct
                    updateUserScore();
                    createNewQuestion();
                }
                else{
                    //user's ans is wrong
                    onGameOver();
                }
            }
        });
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = a+b;
                if(result != currentResult){
                    //user's ans is correct
                    updateUserScore();
                    createNewQuestion();
                }
                else{
                    //user's ans is wrong
                    onGameOver();
                }
            }
        });


    }
    private void createNewQuestion(){
        startCountdownTimer();
        int result;
        a = new Random().nextInt(50);
        b = new Random().nextInt(50);
        result = a+b;
        isResultRight = new Random().nextBoolean();
        if(isResultRight){
            //to generate the correct ans
            String myQuestion = a+" + "+b+" = "+ result;
            ques.setText(myQuestion);
            currentResult = result;
        }
        else{
            //show wrong result
            int fake = new Random().nextInt(5)+1;
            int wrongResult = result + fake;
            String myQuestion = a+" + "+b+" = "+ wrongResult;
            ques.setText(myQuestion);
            currentResult = wrongResult;

        }

    }
    private void updateUserScore(){
        userScore += 10;
        String score = "Your Score = " + userScore;
        your_score.setText(score);
    }
    private void onGameOver(){
        time_left.setText("Time Left = 0 sec");
        if(timer != null)
            timer.cancel();
       AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setTitle("Game Over!").setMessage("Your Score = "+userScore).setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userScore = 0;
                String score = "Your Score = "+userScore;
                your_score.setText(score);
                createNewQuestion();
            }
        }).setCancelable(false);
       //we will add the builder to the dialog
        alertDialog = builder.create();
        alertDialog.show();
    }
    private void startCountdownTimer(){
        if(timer != null)
            timer.cancel();
        timer = new CountDownTimer(31000,1000) {//1 sec = 1000 milli second
            @Override
            public void onTick(long millisUntilFinished) {
                String timeLeftConverter = "Time Left : "+(millisUntilFinished/1000)+" sec";
                time_left.setText(timeLeftConverter);

            }

            @Override
            public void onFinish() {
                onGameOver();
            }
        };
        timer.start();
    }
}