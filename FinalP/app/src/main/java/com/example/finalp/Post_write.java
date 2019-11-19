package com.example.finalp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Post_write extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();//사용자 정보 가져오기
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private EditText mTitle,mContents;//제목, 내용


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        mTitle=findViewById(R.id.Post_write_title);//제목 , item_post.xml의 변수와 혼동주의
        mContents=findViewById(R.id.Post_write_contents);
        findViewById(R.id.Post_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(mAuth.getCurrentUser()!=null){
            String PostID=mStore.collection(FirebaseID.post).document().getId();//제목이 같아도 게시글이 겹치지않게

            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.title,mTitle.getText().toString());//게시글제목
            data.put(FirebaseID.contents,mContents.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            mStore.collection("Post").add(data);//Post라는 테이블에 데이터를 입력하는것
            finish();
        }
    }
}
