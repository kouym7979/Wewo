package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalp.Notice_B.Post;
import com.example.finalp.adapters.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoticeBoardActivity extends AppCompatActivity implements View.OnClickListener, PostAdapter.EventListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mPostRecyclerView;

    private PostAdapter mAdapter;
    private List<Post> mDatas;
    private Button search_btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Foreign Post");

        mPostRecyclerView = findViewById(R.id.recyclerview);
        findViewById(R.id.edit_button).setOnClickListener(this);

        findViewById(R.id.search_btn1).setOnClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;

    }

    @Override
    protected void onStart(){
        super.onStart();
        mDatas = new ArrayList<>();//
        mStore.collection("Post")//리사이클러뷰에 띄울 파이어베이스 테이블 경로
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)//시간정렬순으로
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (queryDocumentSnapshots != null) {
                                    mDatas.clear();//미리 생성된 게시글들을 다시 불러오지않게 데이터를 한번 정리
                                    for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                        Map<String, Object> shot = snap.getData();
                                        String documentId = String.valueOf(shot.get(FirebaseID.documentId));
                                        String title = String.valueOf(shot.get(FirebaseID.title));
                                        String contents = String.valueOf(shot.get(FirebaseID.contents));
                                        String p_nickname = String.valueOf(shot.get(FirebaseID.nickname));
                                        Post data = new Post(documentId, title, contents, p_nickname);
                                        mDatas.add(data);//여기까지가 게시글에 해당하는 데이터 적용
                                    }
                                    mAdapter = new PostAdapter(NoticeBoardActivity.this,mDatas);//mDatas라는 생성자를 넣어줌
                                    mPostRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn1: {
                startActivity(new Intent(this, Search_Post_Activity.class));
                break;
            }
            case R.id.edit_button: {
                startActivity(new Intent(this, Post_write.class));
                break;
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this,"몇 번째"+position,Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this,Post_Comment.class));
    }


}
