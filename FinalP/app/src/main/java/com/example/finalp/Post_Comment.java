package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.finalp.Notice_B.Content;
import com.example.finalp.adapters.PostContentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

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
    private ImageView com_photo;
    private ImageView com_photo2;
    private PostContentAdapter contentAdapter;
    private RecyclerView mCommentRecyclerView;
    private List<Content> mcontent;
    private EditText com_edit;
    private String comment_p,post_t;//
    String sub_pos;//코멘트에 들어가있는 게시글의 위치
    int com_pos = 0;//게시글의 등록된 위치
    private String time;
    private String photoUrl; //사진 저장 변수
    private ToggleButton likeButton; //좋아요 버튼
    private TextView likeText; //좋아요 갯수보여주는 텍스트
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__comment);

        com_nick = (TextView) findViewById(R.id.Comment_nickname);//본문 작성자
        com_title = (TextView) findViewById(R.id.Comment_title);//제목
        com_text = (TextView) findViewById(R.id.Comment_text);//본문
        com_edit = (EditText) findViewById(R.id.Edit_comment);//댓글 작성 내용
        com_photo = (ImageView) findViewById(R.id.Comment_photo); //작성자 프로필 이미지
        com_photo2 =  (ImageView) findViewById(R.id.Comment_photo2); //작성자가 올린 이미지
        likeButton = (ToggleButton) findViewById(R.id.like_button); //좋아요 버튼
        likeText = (TextView) findViewById(R.id.like_text); // 좋아요 개수 보여주는 텍스트
        mCommentRecyclerView = findViewById(R.id.comment_recycler);//코멘트 리사이클러뷰
        Intent intent = getIntent();//데이터 전달받기
        com_pos = intent.getExtras().getInt("position");
        com_nick.setText(intent.getStringExtra("nickname"));
        com_text.setText(intent.getStringExtra("content"));
        com_title.setText(intent.getStringExtra("title"));
        likeText.setText(intent.getStringExtra("like").toString());
        //사진 불러오기
        FirebaseUser user= mAuth.getCurrentUser();
        if(user!=null) {

            if (user.getPhotoUrl() == null) {
                Log.d("사진", "포토유알엘이 비어있어요.");

            }
            if (user.getPhotoUrl() != null) {
                photoUrl = user.getPhotoUrl().toString();
            }
        }

        if ( !intent.getExtras().getString("p_photo").isEmpty()) {
            Log.d("피포토", intent.getExtras().getString("p_photo"));
            Picasso.get()
                    .load(intent.getStringExtra("p_photo"))
                    .into(com_photo);
        }
        else
        {
            Picasso.get()
                    .load(R.drawable.wewo)
                    .into(com_photo);
        }
        String spost_photo=intent.getExtras().getString("post_photo");
        Log.d("String spost값", spost_photo);
        if ( !spost_photo.equals("null")) {
            Log.d("피포토사진있음", intent.getExtras().getString("post_photo"));
            Picasso.get()
                    .load(intent.getStringExtra("post_photo"))
                    .into(com_photo2);
        }
        else
        {
            Log.d("사진빔", "사진이 비어있어요");
            com_photo2.getLayoutParams().height= 0;
            com_photo2.setVisibility(View.GONE);
        }





        post_t=intent.getStringExtra("title");//게시글의 위치
        //time=(String)intent.getSerializableExtra("time");//해당 게시글의 등록 시간



        findViewById(R.id.comment_button).setOnClickListener(this);//댓글 입력 버튼

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
                               comment_p=(String)task.getResult().getData().get(FirebaseID.nickname);//
                                //닉네임 뿐만아니라 여기서 FirebaseID.password를 하면 비밀번호도 받아올 수 있음. 즉 원하는 것을 넣으면 됨
                                //파이어베이스에 등록된 닉네임을 불러옴
                            }
                        }
                    });
        }

        likeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(likeButton.isChecked()){
                    Log.d("토글좋아요켜짐", "켜짐");

                }
                else{
                    Log.d("토글좋아요꺼짐", "꺼짐");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if(Integer.toString(com_pos)==comment_p) {//게시글의 위치와 댓글의 위치가 같은거를 보여줌
        mcontent = new ArrayList<>();//리사이클러뷰에 표시할 댓글 목록
            mStore.collection("Comment")
                    .whereEqualTo("title", post_t)//리사이클러뷰에 띄울 파이어베이스 테이블 경로
                    .orderBy(FirebaseID.timestamp, Query.Direction.ASCENDING)//시간정렬순으로 이건 처음에 작성한게 제일 위로 올라감 게시글과 반대
                    .addSnapshotListener(new EventListener<QuerySnapshot>(){
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (queryDocumentSnapshots != null) {
                                        mcontent.clear();//미리 생성된 게시글들을 다시 불러오지않게 데이터를 한번 정리
                                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                                Map<String, Object> shot = snap.getData();
                                                String documentId = String.valueOf(shot.get(FirebaseID.documentId));
                                                String comment = String.valueOf(shot.get(FirebaseID.comment));
                                                String c_nickname = String.valueOf(shot.get(FirebaseID.nickname));
                                                String c_photo = String.valueOf(shot.get(FirebaseID.c_photo));
                                                Content data = new Content(documentId, c_nickname, comment,  Integer.toString(com_pos),post_t,c_photo);
                                                mcontent.add(data);//여기까지가 게시글에 해당하는 데이터 적용
                                            }
                                        }
                                        contentAdapter = new PostContentAdapter(mcontent);//mDatas라는 생성자를 넣어줌
                                        mCommentRecyclerView.setAdapter(contentAdapter);
                                    }

                            });
    }


    @Override
    public void onClick(View v) {
        if (mAuth.getCurrentUser() != null) {//새로 Comment란 컬렉션에 넣어줌
            Map<String, Object> data = new HashMap<>();
            data.put(FirebaseID.documentId, mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.comment, com_edit.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            data.put(FirebaseID.nickname, comment_p);
            data.put(FirebaseID.c_photo,photoUrl);
            Intent intent = getIntent();//데이터 전달받기
            data.put(FirebaseID.title,post_t);//게시글의 제목을 넣어준다 비교하기위해서
            //Log.d("확인",po)
            com_pos = intent.getExtras().getInt("position");//Post 콜렉션의 게시글 등록위치를 전달받아옴
            //Log.d("확인","위치"+com_pos);
            data.put(FirebaseID.post_position, Integer.toString(com_pos));//작성된 게시판의 위치를 댓글에 저장
            mStore.collection("Comment").add(data);//댓글 콜렉션에 저장
            View view = this.getCurrentFocus();//작성버튼을 누르면 에딧텍스트 키보드 내리게 하기
            if (view != null) {//댓글작성시 키보드 내리고 댓글에 작성한 내용 초기화
                InputMethodManager hide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                hide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                com_edit.setText("");
            }
            finish();
            startActivity(intent);
        }
    }
}