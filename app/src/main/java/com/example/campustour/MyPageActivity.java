package com.example.campustour;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MyPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Button foot_btn = (Button) findViewById(R.id.button_foot);
        foot_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), FootBtnClickedActivity.class);
                startActivity(intent);
            }
        });

        Button quest_btn = (Button) findViewById(R.id.button_quest);
        quest_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), QuestBtnClickedActivity.class);
                startActivity(intent);
            }
        });
    }
}