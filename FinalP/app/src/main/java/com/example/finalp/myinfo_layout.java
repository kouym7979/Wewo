package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class myinfo_layout extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private String nickname;
    private TextView t_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_layout);

        t_nick=(TextView)findViewById(R.id.myinfo_nickname);

        if(mAuth.getCurrentUser()!=null){//UserInfo에 등록되어있는 닉네임을 가져오기 위해서
            mStore.collection("user").document(mAuth.getCurrentUser().getUid())// 여기 콜렉션 패스 경로가 중요해 보면 패스 경로가 user로 되어있어서
                    //우리 파이어베이스의 user 컬렉션의 정보를 가져올 수 있어. email, password, Uid(주민번호같은 거), nickname
                    //현재는 user에는 저 4개의 정보밖에 없어. 만약 다른 정보를 추가로 받고싶다면 어제 말했듯이 너가 가입에서부터 새로 입력값을 받고
                    //FirebaseID에 똑같이 추가하고 SIgnupActivity에 UserMap부분을 수정하면되. 아마 UserInfo에 객체 생성은 따로 안해줘도 될거야 내가 형식을 바꿔놔서
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                                nickname=(String)task.getResult().getData().get(FirebaseID.nickname);//
                                //닉네임 뿐만아니라 여기서 FirebaseID.password를 하면 비밀번호도 받아올 수 있음. 즉 원하는 것을 넣으면 됨
                                //파이어베이스에 등록된 닉네임을 불러옴
                            }
                        }
                    });
        }
        t_nick.setText(nickname);
    }
}
