package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class
SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText sEmail, sPw, sPw_chk, sNickname;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sEmail = findViewById(R.id.semail);
        sPw = findViewById(R.id.spw);
        sPw_chk = findViewById(R.id.spw_chk);
        sNickname = findViewById(R.id.snickname);
        findViewById(R.id.btn_signup).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //비밀번호와 비밀번호 확인이 일치하고 이메일이 공란이 아닐때 조건을 추가해야함
        mAuth.createUserWithEmailAndPassword(sEmail.getText().toString(), sPw.getText().toString())
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if (sEmail!=null && sPw == sPw_chk) {//이메일 아이디를 입력하고 비밀번호와 비밀번호 확인이 일치하면 회원가입가능
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Map<String, Object> userMap = new HashMap<>();//유저의 id,pw를 저장하기 위해 맵으로 선언
                                //FirebaseID란 클래스를 추가로 만들어서 private public string 형식으로 id,pw 값을 저장하는 클래스 생성
                                userMap.put(FirebaseID.documentId, user.getUid());//고유 식별번호
                                userMap.put(FirebaseID.nickname, sNickname.getText().toString());
                                userMap.put(FirebaseID.email, sEmail.getText().toString());//로그인에 사용할 email 아이디
                                userMap.put(FirebaseID.password, sPw.getText().toString());//pw받고

                                //mStore.collection("users").document(user.getUid()).set(userMap, SetOptions.merge());
                                mStore.collection("UserInfo")//users라는 테이블에 데이터를 넣는것
                                        .add(userMap);
                                finish();
                           // }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Signup error.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}

