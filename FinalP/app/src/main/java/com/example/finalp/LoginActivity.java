package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText Em,Pw;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    private CheckBox check1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Em=findViewById(R.id.Email);
        Pw=findViewById(R.id.password);
        check1 = (CheckBox)findViewById(R.id.chk_autologin);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);

        findViewById(R.id.findpw).setOnClickListener(this);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        check1.setChecked(pref.getBoolean("check1", false));
    }


    @Override//자동로그인 함수
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        check1 = (CheckBox)findViewById(R.id.chk_autologin);
        if(check1.isChecked()==true)
        {
            if (currentUser != null) {
                startActivity(new Intent(this, nav.class));
                finish();
            }
        }
    }

    public void onStop()
    {
        super.onStop();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        CheckBox check1 = (CheckBox)findViewById(R.id.chk_autologin);
        editor.putBoolean("check1", check1.isChecked());
        editor.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String s_email=Em.getText().toString();
                String s_pw=Pw.getText().toString();
                if(s_email ==null ||s_pw==null)
                {
                    Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                }

                if(s_email!=null && s_pw!=null) {
                    loginStart(s_email, s_pw);
                }
                break;
            case R.id.signup:
                startActivity(new Intent(this,SignupActivity.class));
                break;
            case R.id.findpw:
                startActivity(new Intent(this,modifyPassword.class));
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
