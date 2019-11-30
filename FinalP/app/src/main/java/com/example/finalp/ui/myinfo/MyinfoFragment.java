package com.example.finalp.ui.myinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
<<<<<<< HEAD
import android.provider.MediaStore;
import android.util.Log;
=======
>>>>>>> parent of 05d263f... Merge pull request #8 from kouym7979/hong
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

<<<<<<< HEAD

import com.example.finalp.FirebaseID;
import com.example.finalp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

=======
import com.example.finalp.R;

public class MyinfoFragment extends Fragment {
>>>>>>> parent of 05d263f... Merge pull request #8 from kouym7979/hong

import java.io.IOException;
import static android.app.Activity.RESULT_OK;

<<<<<<< HEAD
public class MyinfoFragment extends Fragment{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private String nick;
    private TextView myinfoNickname;
    private TextView myinfoEmail;
    private TextView myinfoNation;
    private ImageView myinfoimageView;
    private Button photo_button;
    private Uri uriProfileImage;
    private static final int CHOOSE_IMAGE = 101;
    private ProgressBar progressBar;
    private Button save_button;
    private String profileImageUrl;
    private String photoUrl;
    private ImageView nav_imgView;
=======
>>>>>>> parent of 05d263f... Merge pull request #8 from kouym7979/hong
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);
<<<<<<< HEAD

        myinfoNickname = (TextView)root.findViewById(R.id.myinfo_nickname) ;
        myinfoEmail = (TextView)root.findViewById(R.id.myinfo_email) ;
        photo_button = (Button)root.findViewById(R.id.btn_photo);
        myinfoimageView = (ImageView)root.findViewById(R.id.imgv_photo);
        progressBar = root.findViewById(R.id.progressbar);
        save_button = (Button)root.findViewById(R.id.btn_save);
        myinfoNation = (TextView)root.findViewById(R.id.myinfo_nation);
        //Activity root3=getActivity(R.id.nav);
        //NavigationView navigationView = (NavigationView) root.findViewById(R.id.nav_view);
        // header = navigationView.getHeaderView(0);
        //nav_imgView = (ImageView) header.findViewById(R.id.nav_imgview);
        FirebaseUser user= mAuth.getCurrentUser();


            //사진 불러오기
            photoUrl = user.getPhotoUrl().toString();
            if(user!=null) {
                if (user.getPhotoUrl() == null) {
                    Log.d("사진", "포토유알엘이 비어있어요.");

                }
                if (user.getPhotoUrl() != null) {
                    Log.d("사진", user.getPhotoUrl().toString());
                    Picasso.get()
                            .load(user.getPhotoUrl().toString())
                            .into(myinfoimageView);
                }
            }


        //이메일, 닉네임, 국적 불러오기
        myinfoEmail.setText(user.getEmail().toString());

        if(mAuth.getCurrentUser()!=null){//UserInfo에 등록되어있는 닉네임을 가져오기 위해서
            mStore.collection("user").document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                                Log.d("닉네임","닉:"+task.getResult().getData().get(FirebaseID.nickname));
                                //pp=(String)task.getResult().getData().get(FirebaseID.nickname);//이부분이 안되네
                                myinfoNickname.setText((String)task.getResult().getData().get(FirebaseID.nickname));
                                //파이어베이스에 등록된 닉네임을 불러옴

                                myinfoNation.setText((String)task.getResult().getData().get(FirebaseID.nation));

                            }
                        }
                    });
        }


        //포토버튼 SEARCH FILES버튼이며 이걸 누르면 사진을 선택할 수 있음
        photo_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        //세이브 버튼 SAVE버튼이며 이걸 누르면 유저정보에 저장됨
        save_button.setOnClickListener(new View.OnClickListener(){
=======
        final TextView textView = root.findViewById(R.id.text_myinfo);
        myinfoViewModel.getText().observe(this, new Observer<String>() {
>>>>>>> parent of 05d263f... Merge pull request #8 from kouym7979/hong
            @Override
            public void onClick(View view) {
                saveUserInformation();

            }
        });
        return root;
    }

    private void saveUserInformation(){  //여기서 정보 바꿀 수 있음
        FirebaseUser user = mAuth.getCurrentUser();

        if(profileImageUrl==null)
        {
            Activity root2 = getActivity();
            Toast.makeText(root2, "변경할 사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (user != null) {
                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(profileImageUrl))
                        .build();

                user.updateProfile(profile)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Activity root2 = getActivity();
                                    Toast.makeText(root2, "Profile Updated!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
/*
            Picasso.get()
                    .load(user.getPhotoUrl().toString())
                    .into(nav_imgView);

 */
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!= null)
        {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uriProfileImage);
                myinfoimageView.setImageBitmap(bitmap);

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
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    profileImageUrl=task.getResult().toString();
                                    Log.i("URL",profileImageUrl);
                                }
                            });
                        }
                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            });
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }



}