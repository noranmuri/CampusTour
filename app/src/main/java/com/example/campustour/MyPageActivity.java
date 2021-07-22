package com.example.campustour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyPageActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent main_intent = getIntent();
        String userid = main_intent.getStringExtra("Userid");

        TextView UserIdText = (TextView) findViewById(R.id.UserName);
        UserIdText.setText(userid.toString());

        db.collection("users")
                .whereEqualTo("id", userid.toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userlevel = document.getString("level").toString();
                                TextView UserLevel = (TextView) findViewById(R.id.UserLevel);
                                UserLevel.setText(userlevel.toString());
                            }
                        }
                    }
                });

        db.collection("users")
                .whereEqualTo("id", userid.toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int userCoin = document.getLong("coin").intValue();
                                //String userCoin = document.getString("coin").toString();
                                TextView CoinView = (TextView) findViewById(R.id.UserCoin);
                                CoinView.setText(String.valueOf(userCoin));
                            }
                        }
                    }
                });

        Button foot_btn = (Button) findViewById(R.id.button_foot);
        foot_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), FootBtnClickedActivity.class);
                intent.putExtra("Userid",userid.toString());
                startActivity(intent);
            }
        });

        Button quest_btn = (Button) findViewById(R.id.button_quest);
        quest_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), QuestBtnClickedActivity.class);
                intent.putExtra("Userid",userid.toString());
                startActivity(intent);
            }
        });
    }
}