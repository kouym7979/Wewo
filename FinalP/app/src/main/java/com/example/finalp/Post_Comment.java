package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalp.Notice_B.Content;
import com.example.finalp.adapters.PostContentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
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
    private String comment_p;//
    int sub_pos=0;
    int com_pos=0;//게시글의 등록된 위치 이걸 활용을 못하네
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
        //com_pos=intent.getExtras().getInt("position");


        com_nick.setText(intent.getStringExtra("nickname"));
        com_text.setText(intent.getStringExtra("content"));
        com_title.setText(intent.getStringExtra("title"));
        findViewById(R.id.comment_button).setOnClickListener(this);//댓글 입력 버튼

        //comment_edit=com_edit.getText().toString();//작성된 댓글을 문자열에 저장

        CollectionReference commentRef=mStore.collection("Comment");
       sub_pos=intent.getExtras().getInt("position");
       // Query query=commentRef.whereEqualTo("post_position",Integer.toString(com_pos));
        if(mAuth.getCurrentUser()!=null){//
            mStore.collection("Comment").document()
                    //게시글의 작성자를 받아와서 그 작성자와 맞는 게시글에 댓글정보를 넣기위해서
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                               // comment_p=(String)task.getResult().getData().get(FirebaseID.post_position);//
                                Log.d("위치확인","몇번째");
                            }
                        }
                    });
        }



    }
    @Override
    protected void onStart() {
        super.onStart();
        //if(Integer.toString(com_pos)==comment_p) {//게시글의 위치와 댓글의 위치가 같은거를 보여줌
            mcontent = new ArrayList<>();//리사이클러뷰에 표시할 댓글 목록
            mStore.collection("Comment")
                    //.whereEqualTo("post_position",Integer.toString(com_pos))//리사이클러뷰에 띄울 파이어베이스 테이블 경로
                    .orderBy(FirebaseID.timestamp, Query.Direction.ASCENDING)//시간정렬순으로 이건 처음에 작성한게 제일 위로 올라감 게시글과 반대
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
                                                Content data = new Content(documentId, comment, c_nickname, Integer.toString(com_pos));
                                                //if(Inte)
                                                mcontent.add(data);//여기까지가 게시글에 해당하는 데이터 적용
                                                Log.d("위치","포문안에 들어왔습니다");
                                            }
                                        }
                                    Log.d("위치","포문이 끝났습니다");
                                        contentAdapter = new PostContentAdapter(mcontent);//mDatas라는 생성자를 넣어줌
                                        mCommentRecyclerView.setAdapter(contentAdapter);
                                    }

                            });
       // }
        }


    @Override
    public void onClick(View v) {
        if(mAuth.getCurrentUser()!=null){//새로 Comment란 컬렉션에 넣어줌
            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.comment,com_edit.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            data.put(FirebaseID.nickname,com_nick.getText().toString());
            Intent intent=getIntent();//데이터 전달받기
            com_pos=intent.getExtras().getInt("position");
            //Log.d("확인","위치"+com_pos);
            data.put(FirebaseID.post_position,Integer.toString(com_pos));//작성된 게시판의 위치를 댓글에 저장
            mStore.collection("Comment").add(data);//댓글 콜렉션에 저장
            View view=this.getCurrentFocus();//작성버튼을 누르면 에딧텍스트 키보드 내리게 하기
            if(view!=null){//댓글작성시 키보드 내리고 댓글에 작성한 내용 초기화
                InputMethodManager hide=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                hide.hideSoftInputFromWindow(view.getWindowToken(),0);
                com_edit.setText("");
            }
        }
    }
}