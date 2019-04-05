package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MindfullnessMeditationGame extends AppCompatActivity implements View.OnTouchListener {

    ImageView feather;
    ImageButton hold_button;
    TextView countDownTimer;
    long timerValue;
    int featherSelected;
    Timer timer;
    Animation grow_shrink;
    Boolean animate;
    Context meditationGame;
    ImageButton exit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_meditation_game);
        countDownTimer = (TextView) findViewById(R.id.meditation_time_counter);
        feather = (ImageView) findViewById(R.id.feather_meditation_game);
        hold_button = (ImageButton) findViewById(R.id.meditation_hold_button);
        exit_button = (ImageButton) findViewById(R.id.exit_button_meditation_game);

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hold_button.setOnTouchListener(this);
        grow_shrink = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.infinite_grow_shrink);
        meditationGame = this;

        grow_shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(animate){
                    feather.startAnimation(grow_shrink);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if(getIntent().hasExtra("Feather"))
        {
            featherSelected = getIntent().getIntExtra("Feather", 1);
            if(featherSelected == 1 )
            {
                feather.setImageResource(R.drawable.green_feather);
            }
            else if(featherSelected == 2)
            {
                feather.setImageResource(R.drawable.purple_feather);
            }
            else if(featherSelected == 3)
            {
                feather.setImageResource(R.drawable.whiteish_feather);
            }
            else if(featherSelected == 4)
            {
                feather.setImageResource(R.drawable.blue_feather);
            }
        }
        timerValue = 300000;


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN ) {
            animate =true;
            hold_button.setImageResource(R.drawable.meditation_button_glowing);
            timer = new Timer(timerValue, 1000);
            timer.start();
            feather.startAnimation(grow_shrink);
            return true;
        }
        if((event.getAction() == MotionEvent.ACTION_UP) ) {
            animate = false;
            hold_button.setImageResource(R.drawable.mindfullness_meditation_button);
            timerValue = timer.onPause();
            feather.clearAnimation();
            return false;
        }
        return false;
    }

    class Timer extends CountDownTimer{
        int mins;
        int sec;
        float mod_sec;
        long remaining;

        public Timer(long ms, long countdownInterval)
        {
            super(ms,countdownInterval);
            remaining = ms;
        }
        @Override
        public void onTick(long millisUntilFinished) {
            mins = (int) millisUntilFinished/60000;
            mod_sec = millisUntilFinished % 60000;
            sec = (int) mod_sec / 1000;
            remaining = millisUntilFinished;
            if(sec > 9) {
                countDownTimer.setText(mins + ":" + sec);
            }
            else
            {
                countDownTimer.setText(mins + ":0" + sec);
            }
        }

        @Override
        public void onFinish() {
            Intent to_navigate = new Intent(meditationGame, MindfullnessSelection.class);
            startActivity(to_navigate);
        }

        public long onPause()
        {
            this.cancel();
            return remaining;
        }
    }
}
