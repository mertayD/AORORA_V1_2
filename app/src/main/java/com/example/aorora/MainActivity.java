package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button  login_button;
    Intent surveyPage;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = findViewById(R.id.login_button);
        context = this;

        // Login button ON click Listener
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                surveyPage = new Intent(context, SurveyPage.class);
                startActivity(surveyPage);

            }
        });


    }


}
