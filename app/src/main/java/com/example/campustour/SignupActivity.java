package com.example.campustour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "signupactivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText id;
    private EditText pw;
    private EditText pwcheck;
    private EditText phone;
    private TextView same;
    Button button;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.join_name);
        id = (EditText) findViewById(R.id.join_id);
        pw = (EditText) findViewById(R.id.join_pw);
        pwcheck = (EditText) findViewById(R.id.join_pwcheck);
        phone = (EditText) findViewById(R.id.join_phone);
        button = (Button) findViewById(R.id.join_button);

        user = new User(name.toString() ,
               id.toString() ,pw.toString(),
                pwcheck.toString(), phone.toString());
        same = (TextView)findViewById(R.id.join_pwsame);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().equals("") && !name.getText().toString().equals("") &&
                        !pw.getText().toString().equals("") && !pwcheck.getText().toString().equals("") &&
                        !phone.getText().toString().equals("")) {
                    // 빈칸이 없는 경우
                    if (pw.getText().toString().equals(pwcheck.getText().toString())) {
                        same.setText("비밀번호 일치");
                        createUser(user);
                    } else {
                        same.setText("비밀번호 불일치");
                    }

                } else {
                    // 안채운 필드가 있으면
                    Toast.makeText(SignupActivity.this, "모든 항목 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void createUser(User data) {
        Task<DocumentReference> documentReferenceTask = db.collection("users").add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"회원가입 되었습니다", Toast.LENGTH_LONG).show();
                                Intent login_page = new Intent(SignupActivity.this, LoginActivity.class);

                                startActivity(login_page);
                                finish();
                            }

                        });
                    }



                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    class User {
        private String name;
        private String id;
        private String pw;
        private String pwcheck;
        private String phone;

        public User() {
        }

        public User(String name, String id, String pw, String pwcheck, String phone) {
            this.name = name;
            this.id = id;
            this.pw = pw;
            this.pwcheck = pwcheck;
            this.phone = phone;
        }

        public String getName() {
            return this.name;
        }
        public String getId() {
            return this.id;
        }
        public String getPw() {
            return this.pw;
        }
        public String getPhone() {
            return this.phone;
        }


    }
}