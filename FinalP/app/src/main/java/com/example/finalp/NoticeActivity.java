package com.example.finalp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class NoticeActivity extends AppCompatActivity implements noticeAdapter.MyRecyclerViewClickListener {
    //@SuppressLint("MissingSuperCall")

    public List<CardItem>dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notice);

        RecyclerView recyclerView = findViewById(R.id.notice_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataList=new ArrayList<>();

        dataList.add(new CardItem("가입을 축하드립니다 !!", "WEWO에 가입해 주신 여러분 진심으로 환영합니다!" +"\n"+ "여러분의 보다 나은 문화생활을 위해 최선을 다할 것을 약속 드립니다. 가입인사를 작성하시면 서비스를 " +
                "이용하시는 데에 많은 도움이 되니 참고해주시기 바랍니다."));
        dataList.add(new CardItem("이용 시 주의사항 및 규칙", "WEWO는 현재 한국에 거주중인 유학생들과 한국 학생들과의 원활한 소통을 위해 개설된 공간입니다." +
                "다양한 국적의 학생들이 모인 공간인 만큼 다양한 문화와 관습이 존재하기 때문에 서로의 문화를 비방한다거나 조롱하는 행위는 삼가해 주시기 바랍니다. 적발 시 경고조치 없이 탈퇴조치 될 수 있음을 알려드립니다. 감사합니다. "));
        dataList.add(new CardItem("이메일 관련 안내","가입시 이메일 아이디에는 실제 자신이 사용하는 유효한 이메일을 기입해 주시길 바랍니다."+'\n'+"" +
                "비밀번호 분실 시 아이디에 기입된 이메일로 비밀번호 관련 정보가 발송되오니 꼭 확인해주시길 바랍니다. 감사합니다!"));

        noticeAdapter adapter = new noticeAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(this);


    }

    @Override
    public void onItemClicked(int position) {
        CardItem dict = dataList.get(position);
        Toast.makeText(this,""+dict.getTitle(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), noticeResult.class);

        intent.putExtra("title",dict.getTitle());
        intent.putExtra("contents",dict.getContents());

        startActivity(intent);

    }

    @Override
    public void onShareButtonClicked(int position) {
        Toast.makeText(this,"Share "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLearnMoreButtonClicked(int position) {

        CardItem dict = dataList.get(position);
        Toast.makeText(this,""+dict.getTitle(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), noticeResult.class);

        intent.putExtra("title",dict.getTitle());
        intent.putExtra("contents",dict.getContents());

        startActivity(intent);
    }
}