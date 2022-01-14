package com.example.esc;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {
    EditText et_id;
    EditText et_pass;
    EditText et_password;
    EditText et_phonenum1;
    EditText et_phonenum2;
    EditText et_phonenum3;
    EditText et_school;
    EditText et_grade;
    Button bt_signin;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        et_id = (EditText) findViewById(R.id.et_id);//TODO: 객체 이름 login과 구분
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_phonenum1 = (EditText) findViewById(R.id.et_phonenum1);
        et_phonenum2 = (EditText) findViewById(R.id.et_phonenum2);
        et_phonenum3 = (EditText) findViewById(R.id.et_phonenum3);
        et_school = (EditText) findViewById((R.id.et_school));
        et_grade = (EditText) findViewById(R.id.et_grade);
        bt_signin = (Button) findViewById(R.id.bt_signin);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        bt_signin.setOnClickListener(new View.OnClickListener() {//회원가입 버튼이 눌러졌을때
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String phoneNum = et_phonenum1.getText().toString().trim() + "-" +
                        et_phonenum2.getText().toString().trim() + "-" +
                        et_phonenum3.getText().toString().trim();
                String school = et_school.getText().toString().trim();
                String grade = et_grade.getText().toString().trim();
                if(id.equals("") || password.equals("")) {
                    Toast.makeText(signin.this, "이메일과 비밀번호는 공란이 될 수 없습니다", Toast.LENGTH_SHORT).show();
                } else {
                    if(grade != "") {
                        if(grade.compareTo("1") <= -1 || grade.compareTo("3") >= 1) {
                            et_grade.setError("학년은 반드시 1~3 중 하나의 숫자여야 합니다");
                        } else {
                            if(pass.equals(password)) {
                                firebaseAuth.createUserWithEmailAndPassword(id, pass).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {//가입 성공시
                                        if(task.isSuccessful()) {
                                            FirebaseUser FUser = firebaseAuth.getCurrentUser();
                                            String uid = FUser.getUid();//db에 저장된 유저의 고유번호
                                            User user = new User(id, pass, school, grade, phoneNum);
                                            mDatabase.child("users").child(uid).setValue(user);//db에 유저의 고유번호로
                                            Toast.makeText(signin.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(signin.this, login.class);
                                            intent.putExtra("id", user.getUserEmail());//회원가입시 intent로 이용자의 이메일 넘겨주기
                                            startActivity(intent);
                                            finish();//Todo: 필요한가 여부 따져야함
                                        } else {
                                            Toast.makeText(signin.this, "이미 존재하는 이메일입니다", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(signin.this, "비밀번호가 맞는지 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                }

            }
        });
    }
}
