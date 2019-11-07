package com.example.project_wewo.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_wewo.Main_home;
import com.example.project_wewo.MemberRegisterActivity;
import com.example.project_wewo.R;
import com.example.project_wewo.ui.login.LoginViewModel;
import com.example.project_wewo.ui.login.LoginViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();//파이어베이스 선언

    private LoginViewModel loginViewModel;
    EditText usernameEditText = findViewById(R.id.username);
    EditText passwordEditText = findViewById(R.id.password);
    final Button loginButton = findViewById(R.id.login);
    String Email, PW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        Email = usernameEditText.getText().toString();//입력받은 email을 문자열로 저장
        PW = passwordEditText.getText().toString();//입력받은 pw을 문자열로 저장
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button registerButton = findViewById(R.id.register); //레지스터 버튼

        loginButton.setOnClickListener(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rintent = new Intent(
                        getApplicationContext(),
                        MemberRegisterActivity.class);
                startActivity(rintent);
            }
        });
    }

    @Override
    public void onClick(View v) {//로그인 버튼 누를시에 작동
        mAuth.signInWithEmailAndPassword(Email, PW)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Login error.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        Intent intent= new Intent(
                                getApplicationContext(),
                                Main_home.class);
                        startActivity(intent);
                    }
                });
    }


}