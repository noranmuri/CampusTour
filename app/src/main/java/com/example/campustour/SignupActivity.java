package com.example.campustour;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "signupactivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText id;
    private EditText pw;
    private EditText pwcheck;
    private EditText phone;
    private TextView same;
    private Button copycheck;
    Button button;
    AlertDialog.Builder myAlertBuilder;
    AlertDialog error;
//    User user;
    HashMap<String, Object> newUser = new HashMap<>();
    String[] mission = new String[10];
    String[] foot = new String[10];

    public void onClickShowAlert(View view) {
        myAlertBuilder = new AlertDialog.Builder(SignupActivity.this);
        // alert의 title과 Messege 세팅
        myAlertBuilder.setTitle("아이디 중복");
        myAlertBuilder.setMessage("이미 사용중인 아이디(닉네임) 입니다. 다시 설정해주세요.");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                // OK 버튼을 눌렸을 경우
//                Toast.makeText(getApplicationContext(),"Pressed OK",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        if (!id.getText().toString().equals("")) { //
            db.collection("users")
                    .whereEqualTo("id", id.getText().toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            boolean check = false;
                            if (task.isSuccessful()) { //중복된게 있으면 successful
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // 아이디 같은 계정이 있으면 돌아가!
                                    error = myAlertBuilder.create();
                                    error.show();
                                    check = true;
                                    break;
                                }
                                if (check == false) {
                                    Toast.makeText(SignupActivity.this, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
        else { //아이디 미입력하고 버튼 누르
            Toast.makeText(SignupActivity.this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
        }



    }
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
        copycheck = (Button) findViewById(R.id.button2);


        same = (TextView)findViewById(R.id.join_pwsame);
//        copycheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!id.getText().toString().equals("")) { //
//                    db.collection("users")
//                            .whereEqualTo("id", id.getText().toString())
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) { //중복된게 있으면 successful
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
////                                            if (document.getString("id").toString().equals(id.getText().toString())) {
//                                                // 아이디 같은 계정이 있으면 돌아가!
//                                                error = myAlertBuilder.create();
//                                                error.show();
//                                                break;
////                                            }
//                                        }
//                                        Toast.makeText(SignupActivity.this, "for end 사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
//                                    } else { //중복된게 안나왔으면
//                                        Toast.makeText(SignupActivity.this, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//                else { //아이디 미입력하고 버튼 누르
//                    Toast.makeText(SignupActivity.this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
//                 }
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().equals("") && !name.getText().toString().equals("") &&
                        !pw.getText().toString().equals("") && !pwcheck.getText().toString().equals("") &&
                        !phone.getText().toString().equals("")) {
                    newUser.put("name",name.getText().toString());
                    newUser.put("id",id.getText().toString());
                    newUser.put("pw",pw.getText().toString());
                    newUser.put("phone",phone.getText().toString());
                    newUser.put("level","1학년");
                    newUser.put("coin",0);
                    newUser.put("mission",new ArrayList<String>(10));
                    newUser.put("foot",new ArrayList<String>(10));
//                    user = new User(name.getText().toString() ,
//                            id.getText().toString() ,pw.getText().toString(),
//                            pwcheck.getText().toString(), phone.getText().toString());
                    // 빈칸이 없는 경우
                    Toast.makeText(SignupActivity.this, "모든 항목 입력.", Toast.LENGTH_LONG).show();
                    if (pw.getText().toString().equals(pwcheck.getText().toString())) {
                        same.setText("비밀번호 일치");
                        createUser(newUser);
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

    private void createUser(HashMap<String,Object> data) {
//        Task<DocumentReference> documentReferenceTask =
                db.collection("users").add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                                Toast.makeText(getApplicationContext(),"회원가입 되었습니다", Toast.LENGTH_LONG).show();
                                Intent login_page = new Intent(SignupActivity.this, LoginActivity.class);

                                startActivity(login_page);
                                finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }




//    class User {
//        private String name;
//        private String id;
//        private String pw;
//        private String pwcheck;
//        private String phone;
//        private String level;
//        private int coin;
//
//        public User() {
//        }
//
//
//
//
//        public User(String name, String id, String pw,String pwcheck, String phone) {
//            this.name = name;
//            this.id = id;
//            this.pw = pw;
//            this.pwcheck = pwcheck;
//            this.phone = phone;
//            this.level = "0학년";
//            this.coin = -1;
//        }
//
//        public String getName() {
//            return this.name;
//        }
//        public String getId() {
//            return this.id;
//        }
//        public String getPw() {
//            return this.pw;
//        }
//        public String getPhone() {
//            return this.phone;
//        }
//
//
//    }
}