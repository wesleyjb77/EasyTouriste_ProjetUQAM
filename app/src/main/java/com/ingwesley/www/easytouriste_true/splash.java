package com.ingwesley.www.easytouriste_true;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;


public class splash extends AppCompatActivity {

    ProgressBar progressBar;
    Handler handler;
    Runnable runnable;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        handler=new Handler();

        runnable=new Runnable() {
            @Override
            public void run() {
             progressBar.setVisibility(View.GONE);
            timer.cancel();

            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            handler.post(runnable);
               Intent i=new Intent(splash.this, MainActivity.class);
                startActivity(i);
                splash.this.finish();
            }
        },10000,100);
    }


}
