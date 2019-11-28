package com.example.finalp.ui.myinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalp.FirebaseID;
import com.example.finalp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyinfoFragment extends Fragment {

    private MyinfoViewModel myinfoViewModel;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private String nick;
    private TextView myinfoNickname;
    private TextView myinfoEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myinfoViewModel =
                ViewModelProviders.of(this).get(MyinfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);
        myinfoNickname = (TextView)root.findViewById(R.id.myinfo_nickname) ;
        myinfoEmail = (TextView)root.findViewById(R.id.myinfo_email) ;

        FirebaseUser user= mAuth.getCurrentUser();
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
                            }
                        }
                    });
        }



        return root;
    }
}