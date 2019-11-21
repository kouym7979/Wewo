package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Em,Pw;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Em=findViewById(R.id.Email);
        Pw=findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);
    }
    /*@Override//자동로그인 함수
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
       if(currentUser!=null)
       {
           startActivity(new Intent(this,nav.class));
           finish();
       }
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                String s_email=Em.getText().toString();
                String s_pw=Pw.getText().toString();
                loginStart(s_email,s_pw);
                break;
            case R.id.signup:
                startActivity(new Intent(this,SignupActivity.class));
                break;
        }

    }
    public void loginStart(String email, String password){
        Toast.makeText(LoginActivity.this,"loginStart" ,Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(LoginActivity.this,"mAuth. onComplete 함수" ,Toast.LENGTH_SHORT).show();
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Login error",Toast.LENGTH_SHORT).show();
                    
                }else{
                    currentUser = mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "로그인 성공" + "/" + currentUser.getEmail() + "/" + currentUser.getUid() ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, nav.class));
                    finish();
                }

            }
        });
    }


}



