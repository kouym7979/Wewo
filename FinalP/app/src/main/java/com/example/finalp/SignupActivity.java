package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class
SignupActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private EditText sEmail, sPw, sPw_chk, sNickname;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private Button duplicate_button;

    private CheckBox korean;
    private CheckBox foreigner;
    private String nation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sEmail = findViewById(R.id.semail);
        sPw = findViewById(R.id.spw);
        sPw_chk = findViewById(R.id.spw_chk);
        sNickname = findViewById(R.id.snickname);


        //checkbox 기능 구현
        korean = (CheckBox) findViewById(R.id.korean);
        foreigner = (CheckBox) findViewById(R.id.foreigner);

        korean.setOnCheckedChangeListener(this);
        foreigner.setOnCheckedChangeListener(this);

        //ID 중복 체크 버튼 구현
        duplicate_button = (Button) findViewById(R.id.btn_duplicate);
        duplicate_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.fetchSignInMethodsForEmail(sEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.getResult().getSignInMethods().size() == 0) {
                                    // email not existed
                                    Toast.makeText(getApplicationContext(), "이 이메일을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                    duplicate_button.setEnabled(false);
                                    duplicate_button.setBackgroundColor(Color.GREEN);
                                    duplicate_button.setText("success");
                                } else {
                                    // email existed
                                    Toast.makeText(getApplicationContext(), "이 이메일은 이미 사용중입니다.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });


        //sign up 버튼 구현
        Button signup_button = (Button) findViewById(R.id.btn_signup);
        signup_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (duplicate_button.isEnabled()) //이메일 중복검사가 안되어 있을 때
                {
                    Toast.makeText(getApplicationContext(), "이메일 중복검사를 해주세요", Toast.LENGTH_SHORT).show();
                } else {
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
                                        userMap.put(FirebaseID.nation,nation);//
                                        mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                        //mStore.collection("UserInfo")//users라는 테이블에 데이터를 넣는것
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

        });


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(korean.isChecked()==true){
            foreigner.setChecked(false);
            nation="Korean";

        }
        else if(foreigner.isChecked()==true){
            korean.setChecked(false);
            nation="Foreigner";
        }
    }
}