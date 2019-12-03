package com.example.finalp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalp.HomeActivity;
import com.example.finalp.NoticeActivity;
import com.example.finalp.NoticeBoardActivity;
import com.example.finalp.R;

public class HomeFragment extends Fragment {

    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        context = container.getContext();

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.post1 :
                            Toast.makeText(context,"외국인 친구찾기 게시판",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),NoticeBoardActivity.class));//일단 하나만 연동
                            break ;
                        case R.id.post2 :
                            Toast.makeText(context,"한국인 친구 찾기 게시판",Toast.LENGTH_SHORT).show();

                            break ;
                        case R.id.post3 :
                            Toast.makeText(context,"한국의 문화 예절 게시판",Toast.LENGTH_SHORT).show();
                            break ;
                        case R.id.post4 :
                            Toast.makeText(context,"외국인 학생 질문 게시판",Toast.LENGTH_SHORT).show();
                            break ;
                        case R.id.post5 :
                            Toast.makeText(context,"장터 게시판",Toast.LENGTH_SHORT).show();
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