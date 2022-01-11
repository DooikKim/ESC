package com.example.esc;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
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

public class login extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText et_id;
    EditText et_pass;
    Button login;
    Button signin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        login = (Button) findViewById(R.id.login);
        signin = (Button) findViewById(R.id.signin);
        login.setOnClickListener(new View.OnClickListener() {//login 버튼이 눌러졌을때
            String email = et_id.getText().toString();
            String password = et_pass.getText().toString();

            @Override
            public void onClick(View view) {
                signIn(email, password);
            }
        });
    }
    private void signIn(String email, String password) {
        if(!validateForm()) {
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(login.this, MainActivity.class);
                    intent.putExtra("id", email);//intent에 id를 넘겨줌
                    startActivity(intent);
                } else {
                    Toast.makeText(login.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean validateForm() {//아이디, 비밀번호가 입력되어있는지 확인(blank가 아님)
        boolean valid = true;
        String email = et_id.getText().toString();
        if (TextUtils.isEmpty(email)) { //아이디 editText가 공란이면
            et_id.setError("아이디를 입력해주세요.");
            valid = false;
        } else {
            et_id.setError(null);
        }
        String password = et_pass.getText().toString();
        if (TextUtils.isEmpty(password)) { //비밀번호 editText가 공란이면
            et_pass.setError("비밀번호를 입력해주세요.");
            valid = false;
        } else {
            et_pass.setError(null);
        }
        return valid;
    }
}
