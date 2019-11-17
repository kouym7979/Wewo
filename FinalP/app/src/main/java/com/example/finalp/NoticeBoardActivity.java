package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalp.Notice_B.Post;
import com.example.finalp.adapters.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoticeBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mPostRecyclerView;

    private PostAdapter mAdapter;
    private List<Post> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        mPostRecyclerView = findViewById(R.id.recyclerview);
        findViewById(R.id.edit_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();//
        mStore.collection(FirebaseID.post)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {//게시글에 해당하는 제목 내용 등을 가져오기 위해
                            if (task.getResult() != null) {
                                for (DocumentSnapshot snap : task.getResult()) {
                                    Map<String,Object> shot=snap.getData();
                                    String documentId=String.valueOf(shot.get(FirebaseID.documentId));
                                    String title=String.valueOf(shot.get(FirebaseID.title));
                                    String contents=String.valueOf(shot.get(FirebaseID.contents));
                                    Post data=new Post(documentId,title,contents);
                                    mDatas.add(data);//여기까지가 게시글에 해당하는 데이터 적용
                                }
                                mAdapter = new PostAdapter(mDatas);//mDatas라는 생성자를 넣어줌
                                mPostRecyclerView.setAdapter(mAdapter);
                            }

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, Post_write.class));
    }
}

