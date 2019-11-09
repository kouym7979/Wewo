package com.example.wewo;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewo.FirebaseID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MemberRegister extends AppCompatActivity implements View.OnClickListener {
    EditText Em_Text,pw_Text,pwck_Text;//em_Text는 이메일 입력하는 에딧
    String sEmail, sPw, sPw_chk;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();//파이어베이스 선언
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();//유저를 저장할 mStore선언
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //activity_register.xml 파일에서 회원가입한 값을 가져옴
        Em_Text = (EditText)findViewById(R.id.et_email);
        pw_Text = (EditText)findViewById(R.id.et_Password);
        pwck_Text = (EditText)findViewById(R.id.et_Password_chk);

        Button registerButton = (Button) findViewById(R.id.memberrequest);

        registerButton.setOnClickListener(this);//회원가입 버튼 눌르면 아래 onClick 작동
    }

    @Override
    public void onClick(View v) {//회원가입 버튼 눌렀을때
        /*버튼을 눌렀을 때 동작하는 소스*/
        sEmail = Em_Text.getText().toString();
        sPw = pw_Text.getText().toString();
        sPw_chk =  pwck_Text.getText().toString();

        if(sPw.equals(sPw_chk))
        {
            //패스워드 확인이 일치
            //아래 코드는 파이어베이스에 새로운 유저 가입에 해당하는 코드
            mAuth.createUserWithEmailAndPassword(sEmail, sPw)//여기엔 edittext에 기입한 문자열을 가져와야함
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //가입한 유저를 firebase store에 저장함
                                if(user!=null) {
                                    Map<String, Object> userMap = new HashMap<>();//유저의 id,pw를 저장하기 위해 맵으로 선언
                                    //FirebaseID란 클래스를 추가로 만들어서 private public string 형식으로 id,pw 값을 저장하는 클래스 생성
                                    userMap.put(FirebaseID.userEmail,sEmail);//고유아이디 설정 해줌
                                    userMap.put(FirebaseID.userPW, sPw);//pw받고
                                    userMap.put(FirebaseID.userID, user.getUid());//email받을까
                                    mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                    //실질적으로 파이어베이스에 저장하는 부분
                                    finish();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MemberRegister.this, "Sign up failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }

        else
        {
            //패스워드 확인이 불일치
            Toast.makeText(MemberRegister.this, "Sign up failed.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
