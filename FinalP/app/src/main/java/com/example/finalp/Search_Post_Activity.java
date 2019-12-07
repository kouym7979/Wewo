package com.example.finalp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalp.Notice_B.Post;
import com.example.finalp.adapters.PostAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Search_Post_Activity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
        private List<Post> mDatas;
    private EditText search;
    private Button s_btn;
    private String search_edit,post_n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__post_);

        mPostRecyclerView = findViewById(R.id.recyclerview);
        search=findViewById(R.id.search);//검색어
        search_edit= getIntent().getStringExtra("search");
        Log.d("확인","검색어:"+search_edit);
        findViewById(R.id.search_btn).setOnClickListener(this);
        findViewById(R.id.edit_button).setOnClickListener(this);
        post_n=getIntent().getStringExtra("post");
    }
    @Override
    protected void onStart(){
        super.onStart();
        mDatas = new ArrayList<>();//
        mStore.collection("Post")//리사이클러뷰에 띄울 파이어베이스 테이블 경로
                .whereEqualTo("title",search_edit)//게시판 제목중에 검색어와 똑같으면 검색
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
                                        String title=String.valueOf(shot.get(FirebaseID.title));
                                        String contents = String.valueOf(shot.get(FirebaseID.contents));
                                        String c_nickname = String.valueOf(shot.get(FirebaseID.nickname));
                                        String p_photo = String.valueOf(shot.get(FirebaseID.p_photo));
                                        String post_photo=String.valueOf(shot.get(FirebaseID.post_photo));
                                        int like = FirebaseID.like;                                        
                                        String post_id=String.valueOf(shot.get(FirebaseID.post_id));
                                        String writer_id=String.valueOf(shot.get(FirebaseID.writer_id));
                                        Post data = new Post(documentId, title, contents, c_nickname, p_photo,post_n,post_photo,post_id,writer_id,like);

                                        mDatas.add(data);//여기까지가 게시글에 해당하는 데이터 적용
                                    }
                                    mAdapter = new PostAdapter(Search_Post_Activity.this,mDatas);//mDatas라는 생성자를 넣어줌
                                    mPostRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_btn:
            {
                search_edit=search.getText().toString();//검색어를 문자열로 추출
                Log.d("확인","검색내용:"+search_edit);
                View view = this.getCurrentFocus();//작성버튼을 누르면 에딧텍스트 키보드 내리게 하기
                if (view != null) {//댓글작성시 키보드 내리고 댓글에 작성한 내용 초기화
                    InputMethodManager hide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    hide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    search.setText("");
                }
                break;
            }
            case R.id.edit_button:startActivity(new Intent(this, Post_write.class));
            break;
        }
    }
}
