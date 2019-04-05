package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {

    GestureDetector gestureDetector;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton butterfly_selection_button;
    ImageButton jar_button;
    ImageButton back_button;
    ImageButton settings_button;
    ImageButton pollen_button;
    TextView user_name_tv;
    TextView user_score_tv;
    TextView butterfly_name_tv;
    TextView butterfly_description_tv;
    Context profilePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        butterfly_selection_button = (ImageButton) findViewById(R.id.profile_butterfly_button);
        jar_button = (ImageButton) findViewById(R.id.jar_button_profile_page);
        pollen_button = (ImageButton) findViewById(R.id.pollen_button_profile_page);
        back_button = (ImageButton) findViewById(R.id.back_button_profile_page);
        settings_button = (ImageButton) findViewById(R.id.settings_button_profile_page);
        user_name_tv = (TextView) findViewById(R.id.profile_user_name_tv);
        user_score_tv = (TextView) findViewById(R.id.profile_user_score);
        butterfly_name_tv = (TextView) findViewById(R.id.profile_page_bf_name_tv);
        butterfly_description_tv = (TextView) findViewById(R.id.profile_page_bf_desc_tv);
        profilePage = this;

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        jar_button.setOnClickListener(this);
        butterfly_selection_button.setOnClickListener(this);
        back_button.setOnClickListener(this);
        gestureDetector = new GestureDetector(profilePage, ProfilePage.this);


    }


    @Override
    public void onClick(View v) {
        int view_id =  v.getId();
        Intent to_navigate;
        if(view_id == butterfly_selection_button.getId() || view_id == jar_button.getId())
        {
            to_navigate = new Intent(profilePage, ButterflyCollectionPage.class);
            startActivity(to_navigate);

        }
        else if(view_id == community_button_bottombar.getId())
        {
            to_navigate = new Intent(profilePage, CommunityPage.class);
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(profilePage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(profilePage, HomeScreen.class);
            startActivity(to_navigate);

        }
        else if(view_id == back_button.getId())
        {
            to_navigate = new Intent(profilePage, HomeScreen.class);
            startActivity(to_navigate);
        }
    }

    @Override
    public boolean onFling (MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y)
    {
        Intent to_navigate;
        if (motionEvent2.getX() - motionEvent1.getX() > 50) {
            to_navigate = new Intent(profilePage, HomeScreen.class);
            startActivity(to_navigate);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            return true;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

}
