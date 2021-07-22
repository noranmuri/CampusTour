package com.example.campustour;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashimage = (ImageView) findViewById(R.id.splashimage);
        TextView splashtext = (TextView) findViewById(R.id.splashtext);

        Animation animation_image = AnimationUtils.loadAnimation(this, R.anim.splash_image);
        Animation animation_text = AnimationUtils.loadAnimation(this, R.anim.splash_text);
        splashimage.startAnimation(animation_image);
        splashtext.startAnimation(animation_text);

        animation_image.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation){ }
            @Override
            public void onAnimationRepeat(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1500);

            }
        });

    }
}