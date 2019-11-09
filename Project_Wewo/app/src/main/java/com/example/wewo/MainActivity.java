package com.example.wewo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();//파이어베이스 선언
    String Email, PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        final Button registerButton = findViewById(R.id.register); //레지스터 버튼

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        //findViewById(R.id.login).setOnClickListener(this);
        //findViewById(R.id.register).setOnClickListener(this);
        Email = usernameEditText.getText().toString();
        PW = passwordEditText.getText().toString();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.login:
               /* mAuth.signInWithEmailAndPassword(Email, PW)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(MainActivity.this,Main_home.class));//성공하면 홈화면으로 이동
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Login error.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });*/
               Intent intent=new Intent(
                       getApplicationContext(),
                       Main_home.class);
               startActivity(intent);
                break;
            case R.id.register:
                Intent rintent= new Intent(
                        getApplicationContext(),
                        MemberRegister.class);
                startActivity(rintent);
                break;

        }
    }

}
