package com.example.finalp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Post_Update extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();//사용자 정보 가져오기
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private EditText mTitle,mContents;//제목, 내용
    private String p_nickname;//게시판에 표기할 닉네잉 //이게 가져온 값을 저장하는 임시 변수
    private ImageButton post_photo;
    private String photoUrl; //사진 저장 변수
    private String post_num,post_id,writer_id;
    private Uri uriProfileImage;
    private ImageView post_imageView;
    private String postImageUrl;
    private static final int CHOOSE_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__update);

        mTitle=findViewById(R.id.Post_write_title);//제목 , item_post.xml의 변수와 혼동주의
        mContents=findViewById(R.id.Post_write_contents);
        findViewById(R.id.Post_save).setOnClickListener(this);
        post_photo =findViewById(R.id.Post_photo);
        post_imageView = findViewById(R.id.post_imageview);
        post_imageView.setVisibility(View.INVISIBLE);

        if(mAuth.getCurrentUser()!=null){//UserInfo에 등록되어있는 닉네임을 가져오기 위해서
            mStore.collection("user").document(mAuth.getCurrentUser().getUid())//
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                                p_nickname=(String)task.getResult().getData().get(FirebaseID.nickname);//
                                writer_id=(String)task.getResult().getData().get(FirebaseID.documentId);
                                Log.d("확인","현재 사용자 uid입니다:"+writer_id);
                            }
                        }
                    });
        }

        post_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

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

        Intent intent=getIntent();
        post_num=intent.getStringExtra("post");
        Log.d("확인","여기는 게시글 작성위:"+post_num);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!= null)
        {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uriProfileImage);
                post_imageView.setImageBitmap(bitmap);
                post_imageView.setVisibility(View.VISIBLE);

                uploadImageToFirebaseStorage();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void uploadImageToFirebaseStorage() {
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis() + ".jpg");

        if(uriProfileImage !=null)
        {
            // progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressBar.setVisibility(View.GONE);
                            profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    postImageUrl=task.getResult().toString();
                                    Log.i("postURL",postImageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressBar.setVisibility(View.GONE);

                        }
                    });
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Post Image"), CHOOSE_IMAGE);
    }

    @Override
    public void onClick(View v) {

        if(mAuth.getCurrentUser()!=null){
            String PostID=mStore.collection("Post").document().getId();//제목이 같아도 게시글이 겹치지않게
            Intent intent=getIntent();
            post_num=intent.getStringExtra("number");
            Log.d("확인","여기는 게시글 작성:"+post_num);
            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());//유저 고유번호
            data.put(FirebaseID.title,mTitle.getText().toString());//게시글제목
            data.put(FirebaseID.contents,mContents.getText().toString());//게시글 내용
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());//파이어베이스 시간을 저장 그래야 게시글 정렬이 시간순가능
            data.put(FirebaseID.nickname,p_nickname);
            data.put(FirebaseID.p_photo,photoUrl);
            data.put(FirebaseID.post_num,post_num);
            data.put(FirebaseID.post_id,PostID);//게시글 ID번호
            data.put(FirebaseID.writer_id,writer_id);
            if(!TextUtils.isEmpty(postImageUrl))
            {
                data.put(FirebaseID.post_photo,postImageUrl);
            }
            mStore.collection("Post").document(intent.getStringExtra("Postid")).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });//Post라는 테이블에 데이터를 입력하는것/ 문서 이름을 PostID로 등록
            //startActivity(new Intent(this,Post_Comment.class));
            finish();
        }
    }
}
