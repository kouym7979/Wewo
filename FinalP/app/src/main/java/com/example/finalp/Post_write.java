package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Post_write extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();//사용자 정보 가져오기
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private EditText mTitle,mContents;//제목, 내용
    private String p_nickname;//게시판에 표기할 닉네잉

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        mTitle=findViewById(R.id.Post_write_title);//제목 , item_post.xml의 변수와 혼동주의
        mContents=findViewById(R.id.Post_write_contents);
        findViewById(R.id.Post_save).setOnClickListener(this);

        if(mAuth.getCurrentUser()!=null){//UserInfo에 등록되어있는 닉네임을 가져오기 위해서
            Toast.makeText(Post_write.this,"여기까지는됨",Toast.LENGTH_SHORT).show();
            Log.d("확인","여기까지되나?");
            mStore.collection("UserInfo").document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                                Log.d("확인","여기까지되나?여기 못들어오나");
                                DocumentSnapshot document=task.getResult();
                                //Log.d("닉네임","nickname:"+task.getResult().getData().get(FirebaseID.nickname));
                            //   p_nickname=(String)document.getData().get(FirebaseID.nickname);//이부분이 안되네
                                //파이어베이스에 등록된 닉네임을 불러옴
                            }
                        }
                    });

        }

    }

    @Override
    public void onClick(View v) {
        if(mAuth.getCurrentUser()!=null){
            String PostID=mStore.collection("Post").document().getId();//제목이 같아도 게시글이 겹치지않게

            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.title,mTitle.getText().toString());//게시글제목
            data.put(FirebaseID.contents,mContents.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            data.put(FirebaseID.nickname,p_nickname);
            mStore.collection("Post").add(data);//Post라는 테이블에 데이터를 입력하는것
            finish();
        }
    }
}
