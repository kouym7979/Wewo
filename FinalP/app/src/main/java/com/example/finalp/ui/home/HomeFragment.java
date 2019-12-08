package com.example.finalp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalp.FirebaseID;
import com.example.finalp.HomeActivity;
import com.example.finalp.NoticeActivity;
import com.example.finalp.NoticeBoardActivity;
import com.example.finalp.R;
import com.example.finalp.Search_Post_Activity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private Context context;
    private String verify;
    FirebaseUser user=Auth.getCurrentUser();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        context = container.getContext();
        
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.post1 :
                                Toast.makeText(context, "외국인 친구찾기 게시판", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), NoticeBoardActivity.class);
                                intent.putExtra("post", "post1");
                                Log.d("확인", "여기는 게시글 작성:");
                                startActivity(intent);
                            break ;
                        case R.id.post2 :
                            Toast.makeText(context,"한국인 친구 찾기 게시판",Toast.LENGTH_SHORT).show();
                            Intent intent2=(new Intent(getActivity(),NoticeBoardActivity.class));
                            intent2.putExtra("post","post2");
                            startActivity(intent2);
                            break ;
                        case R.id.post3 :
                            Toast.makeText(context,"한국의 문화 예절 게시판",Toast.LENGTH_SHORT).show();
                            Intent intent3=(new Intent(getActivity(),NoticeBoardActivity.class));
                            intent3.putExtra("post","post3");
                            startActivity(intent3);
                            break ;
                        case R.id.post4 :
                            Toast.makeText(context,"외국인 학생 질문 게시판",Toast.LENGTH_SHORT).show();
                            Intent intent4=(new Intent(getActivity(),NoticeBoardActivity.class));
                            intent4.putExtra("post","post4");
                            startActivity(intent4);
                            break ;
                        case R.id.post5 :
                            Toast.makeText(context,"장터 게시판",Toast.LENGTH_SHORT).show();
                            Intent intent5=(new Intent(getActivity(),NoticeBoardActivity.class));
                            intent5.putExtra("post","post5");
                            startActivity(intent5);
                            break ;
                        case R.id.post6 :
                            Toast.makeText(context,"공지사항",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), NoticeActivity.class));
                            break ;
                    }
                }
            } ;
        Button post1_button = (Button) view.findViewById(R.id.post1) ;
        post1_button.setOnClickListener(onClickListener) ;
        Button post2_button = (Button) view.findViewById(R.id.post2) ;
        post2_button.setOnClickListener(onClickListener) ;
        Button post3_button = (Button) view.findViewById(R.id.post3) ;
        post3_button.setOnClickListener(onClickListener) ;
        Button post4_button = (Button) view.findViewById(R.id.post4) ;
        post4_button.setOnClickListener(onClickListener) ;
        Button post5_button = (Button) view.findViewById(R.id.post5) ;
        post5_button.setOnClickListener(onClickListener) ;
        Button post6_button = (Button) view.findViewById(R.id.post6) ;
        post6_button.setOnClickListener(onClickListener) ;

        return view;
    }
}