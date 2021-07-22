package com.example.campustour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginActivity extends AppCompatActivity {

    private EditText id;
    private EditText pw;
    private Button btn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //회원가입 하러 가기
        TextView signup = (TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"회원가입 하세요", Toast.LENGTH_LONG).show();
                Intent signup_page = new Intent(LoginActivity.this,SignupActivity.class);

                startActivity(signup_page);
                //finish(); // 이거 있으면 뒤로가기 누르면 앱자체가 꺼짐
            }

        });

        //로그인
        id = (EditText) findViewById(R.id.login_id);
        pw = (EditText) findViewById(R.id.login_pw);
        btn = (Button) findViewById(R.id.login_btn);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!id.getText().toString().equals("") && !pw.getText().toString().equals("")) {
//                    login(id.getText().toString());
////                    Intent main_page = new Intent(LoginActivity.this, MainActivity.class);
////                    Toast.makeText(LoginActivity.this, "로그인 성공이요~", Toast.LENGTH_SHORT).show();
////                    startActivity(main_page);
////                    Log.d("DB", "로그인성공했다고!!!!");
////                    finish();
//
//                } else {
//                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
//                }
//            }
//        });



    }

    public void go_login(View view) {

        if (!id.getText().toString().equals("") && !pw.getText().toString().equals("")) {

            db.collection("users")
                    .whereEqualTo("id", id.getText().toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getString("id").toString().equals(id.getText().toString()) ) {
                                        if (document.getString("pw").toString().equals(pw.getText().toString())) {
                                            Intent main_page = new Intent(LoginActivity.this, MainActivity.class);
                                            main_page.putExtra("Userid",id.getText().toString());
                                            Toast.makeText(LoginActivity.this, "로그인 성공이요~", Toast.LENGTH_SHORT).show();
                                            startActivity(main_page);
                                            finish();

                                            break;
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Log.d("DB", document.getData()+" ***** "+document.getData().containsValue(id));
                                }
                            } else {
                                // 로그인 실패
                                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
        }
    }

}