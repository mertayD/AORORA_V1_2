package com.example.aorora;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.sql.Types.INTEGER;

public class MindfullnessBreathingGame extends AppCompatActivity {

    static ImageButton exit_button;
    ImageView inhale_button;
    ImageView butterfly_image;
    TextView remaining_breaths;
    TextView remaining_sec;
    Animation enlarge;
    Animation shrink;
    Vibrator myVibrate;
    boolean isRun;
    boolean clickable;
    boolean isTwoDigit;
    static int count;
    static boolean cont;
    Context mindfullness_breathing_game;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_breathing_game);

        // checks if inside the run because as soon as it finishes run it goes to cancel
        isRun = false;
        clickable = true;
        cont = false;
        mindfullness_breathing_game = this;

        exit_button = (ImageButton) findViewById(R.id.exit_button_breathing);
        inhale_button = (ImageView) findViewById(R.id.breathing_inhale_button);
        butterfly_image = (ImageView) findViewById(R.id.butterfly_image_breathinggame);
        remaining_breaths = (TextView) findViewById(R.id.breath_count_tv);
        remaining_sec = (TextView) findViewById(R.id.remaining_time_breathing_tv);

        myVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        exit_button.setVisibility(View.INVISIBLE);

        remaining_sec.setText("3 Seconds");
        if(getIntent().hasExtra("TimerValue"))
        {
            int text = getIntent().getIntExtra("TimerValue", 1);
            if(text == 1)
            {
                text = 3;
            }
            else if( text == 2)
            {
                text = 15;
            }
            else{
                text = 20;
            }
            remaining_breaths.setText(text + " Breaths");
        }
        enlarge = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.myanimation);
        shrink = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_to_original);

        final Handler handler = new Handler();
        final Runnable mLongPressed = new Runnable() {
            public void run() {
                Log.d("VERBOSE", "run: INSIDE RUN ");
                butterfly_image.startAnimation(shrink);
                isRun = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    myVibrate.vibrate(500);
                }

                String temp = remaining_breaths.getText().toString();
                String counted = "";
                int index = 0;
                while (index<2)
                {
                    char current = temp.charAt(index);
                    if(current != ' ') {
                        counted = counted + current;
                    }
                    index++;
                }
                count = Integer.parseInt(counted);
                if(cont)
                {
                    count = count + 1;
                }
                else
                {
                    count = count -1;
                }
                remaining_breaths.setText(count + " Breaths");
            }
        };


        inhale_button.setOnTouchListener(new View.OnTouchListener() {
            Timer myTimer= new Timer(3000,100);

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && clickable) {
                    butterfly_image.startAnimation(enlarge);
                    handler.postDelayed(mLongPressed, 3000);
                    myTimer.start();
                    return true;
                }
                if((event.getAction() == MotionEvent.ACTION_UP) && clickable) {
                    handler.removeCallbacks(mLongPressed);
                    Log.d("VERBOSE", "run: INSIDE CANCEL");
                    if(!isRun){
                        remaining_sec.setText("3 Seconds");
                        butterfly_image.clearAnimation();
                        myTimer.cancel();
                    }
                    return false;
                }
                return false;
            }
        });

        shrink.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("VERBOSE", "run: INSIDE 2nd ANIM");
                clickable = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(count == 0)
                {
                    DialogFragment newFragment = new BreathDialog();
                    newFragment.show(getSupportFragmentManager(), "CONTINUE");                }
                else
                {
                    remaining_sec.setText("3 Seconds");
                    myVibrate.vibrate(500);
                }
                isRun = false;
                clickable = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_navigate = new Intent(mindfullness_breathing_game, MindfullnessBreathing.class);
                startActivity(to_navigate);
            }
        });

    }

    class Timer extends CountDownTimer{

        public Timer(long ms, long countdownInterval)
        {
            super(ms,countdownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if(millisUntilFinished < 2000 ){
                remaining_sec.setText("2 Seconds");
            }
            if(millisUntilFinished < 1000 ){
                remaining_sec.setText("1 Seconds");
            }

        }
        @Override
        public void onFinish() {
            remaining_sec.setText("0 Seconds");
        }
    }

    public static class BreathDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Congratulations! You have finished the Mindfullness Breathing! Would you like to Continue?")
                    .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            exit_button.setVisibility(View.VISIBLE);
                            cont = true;
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            exit_button.performClick();
                        }
                    });

            return builder.create();
        }
    }
}
