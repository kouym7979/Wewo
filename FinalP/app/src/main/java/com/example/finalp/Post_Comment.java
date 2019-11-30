package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalp.Notice_B.Content;
import com.example.finalp.adapters.PostContentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post_Comment extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView com_title;
    private TextView com_text;
    private TextView com_nick;
    private PostContentAdapter contentAdapter;
    private RecyclerView mCommentRecyclerView;
    private List<Content> mcontent;
    private EditText com_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__comment);

        com_nick=(TextView)findViewById(R.id.Comment_nickname);//본문 작성자
        com_title=(TextView)findViewById(R.id.Comment_title);//제목
        com_text=(TextView)findViewById(R.id.Comment_text);//본문
        com_edit=(EditText)findViewById(R.id.Edit_comment);//댓글 작성 내용
        mCommentRecyclerView=findViewById(R.id.comment_recycler);//코멘트 리사이클러뷰


        Intent intent=getIntent();//데이터 전달받기

        int com_pos=intent.getExtras().getInt("position");//게시글의 등록된 위치 이걸 활용을 못하네

        com_nick.setText(intent.getStringExtra("nickname"));
        com_text.setText(intent.getStringExtra("content"));
        com_title.setText(intent.getStringExtra("title"));

        findViewById(R.id.comment_button).setOnClickListener(this);//댓글 입력 버튼

        //comment_edit=com_edit.getText().toString();//작성된 댓글을 문자열에 저장

        /*if(mAuth.getCurrentUser()!=null){//
            mStore.collection("Post").document()//이부분이 에러
                    //게시글의 작성자를 받아와서 그 작성자와 맞는 게시글에 댓글정보를 넣기위해서
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                                post_uid=(String)task.getResult().getData().get(FirebaseID.documentId);//
                                //닉네임 뿐만아니라 여기서 FirebaseID.password를 하면 비밀번호도 받아올 수 있음. 즉 원하는 것을 넣으면 됨
                                //파이어베이스에 등록된 닉네임을 불러옴
                            }
                        }
                    });
        }*/



    }
    @Override
    protected void onStart(){
        super.onStart();
       mcontent = new ArrayList<>();//리사이클러뷰에 표시할 댓글 목록
        mStore.collection("Comment")//리사이클러뷰에 띄울 파이어베이스 테이블 경로
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)//시간정렬순으로
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (queryDocumentSnapshots != null) {
                                    mcontent.clear();//미리 생성된 게시글들을 다시 불러오지않게 데이터를 한번 정리
                                    for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                        Map<String, Object> shot = snap.getData();
                                        String documentId = String.valueOf(shot.get(FirebaseID.documentId));
                                        String comment = String.valueOf(shot.get(FirebaseID.comment));
                                        String c_nickname = String.valueOf(shot.get(FirebaseID.nickname));
                                        Content data = new Content(documentId, comment, c_nickname);
                                        mcontent.add(data);//여기까지가 게시글에 해당하는 데이터 적용
                                    }
                                    contentAdapter = new PostContentAdapter(mcontent);//mDatas라는 생성자를 넣어줌
                                    mCommentRecyclerView.setAdapter(contentAdapter);
                                }
                            }
                        });
    }

    @Override
    public void onClick(View v) {
        if(mAuth.getCurrentUser()!=null){//새로 Comment란 컬렉션에 넣어줌
            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.comment,com_edit.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            data.put(FirebaseID.nickname,com_nick.getText().toString());
            data.put(FirebaseID.post_id,com_title.getText().toString());//게시글 제목으로 구별하기 위해
            //data.put(FirebaseID.position,intent.getExtras().getInt("position"));
            mStore.collection("Comment").add(data);//댓글 콜렉션에 저장
            finish();
        }
    }
}