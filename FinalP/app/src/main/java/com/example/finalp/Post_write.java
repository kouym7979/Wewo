package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class Post_write extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();//사용자 정보 가져오기
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private EditText mTitle,mContents;//제목, 내용
    private String p_nickname;//게시판에 표기할 닉네잉 //이게 가져온 값을 저장하는 임시 변수
    private String photoUrl; //사진 저장 변수
    private int post_num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        mTitle=findViewById(R.id.Post_write_title);//제목 , item_post.xml의 변수와 혼동주의
        mContents=findViewById(R.id.Post_write_contents);
        findViewById(R.id.Post_save).setOnClickListener(this);

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
                                p_nickname=(String)task.getResult().getData().get(FirebaseID.nickname);//
                                //닉네임 뿐만아니라 여기서 FirebaseID.password를 하면 비밀번호도 받아올 수 있음. 즉 원하는 것을 넣으면 됨
                                //파이어베이스에 등록된 닉네임을 불러옴
                            }
                        }
                    });
        }

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
    }


    @Override
    public void onClick(View v) {
        post_num++;//작성버튼이 눌리면 게시글넘버 1증가
        if(mAuth.getCurrentUser()!=null){
            String PostID=mStore.collection("Post").document().getId();//제목이 같아도 게시글이 겹치지않게

            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.title,mTitle.getText().toString());//게시글제목
            data.put(FirebaseID.contents,mContents.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            data.put(FirebaseID.nickname,p_nickname);
            data.put(FirebaseID.p_photo,photoUrl);
            //data.put(FirebaseID.nickname,p_nickname);
            //data.put(FirebaseID.post_time,)
            mStore.collection("Post").add(data);//Post라는 테이블에 데이터를 입력하는것
            finish();
        }
    }
}