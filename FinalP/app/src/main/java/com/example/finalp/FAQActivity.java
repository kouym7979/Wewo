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

public class FAQActivity extends AppCompatActivity implements noticefaqAdapter.MyRecyclerViewClickListener {
    //@SuppressLint("MissingSuperCall")

    public List<faqCardItem>dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_faq);

        RecyclerView recyclerView = findViewById(R.id.faq_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataList=new ArrayList<>();

        dataList.add(new faqCardItem("가입인사는 어디에 남기나요?", "▶ 가입인사는 처음 WEWO를 이용하는 학생이 자신을 먼저 소개함으로써 " +
                "다른 학생들에게 먼저 다가가 거리감을 좁힐 수 있도록 하기 위한 서비스 입니다." + '\n' +
                "따라서 외국인 학생 친구 사귀기 게시판이나 한국인 친구 사귀기 게시판 상관없이 " +
                "자신을 소개하고 싶은 게시판을 선택하시면 좋을 것 같습니다"+'\n'+
                "자신이 한국인인데 외국인 친구를 사귀고 싶다면 외국인 친구 사귀기 게시판을 이용하면 좋겠죠?"));
        dataList.add(new faqCardItem("이용 시 주의사항 및 규칙", "WEWO는 현재 한국에 거주중인 유학생들과 한국 학생들과의 원활한 소통을 위해 개설된 공간입니다." +
                "다양한 국적의 학생들이 모인 공간인 만큼 다양한 문화와 관습이 존재하기 때문에 서로의 문화를 비방한다거나 조롱하는 행위는 삼가해 주시기 바랍니다. 적발 시 경고조치 없이 탈퇴조치 될 수 있음을 알려드립니다. 감사합니다. "));
        dataList.add(new faqCardItem("이메일 관련 안내","가입시 이메일 아이디에는 실제 자신이 사용하는 유효한 이메일을 기입해 주시길 바랍니다."+'\n'+"" +
                "비밀번호 분실 시 아이디에 기입된 이메일로 비밀번호 관련 정보가 발송되오니 꼭 확인해주시길 바랍니다. 감사합니다!"));

        noticefaqAdapter adapter = new noticefaqAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(this);


    }

    @Override
    public void onItemClicked(int position) {
        faqCardItem dict = dataList.get(position);
        Toast.makeText(this,""+dict.getTitle(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), noticefaqResult.class);

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

        faqCardItem dict = dataList.get(position);
        Toast.makeText(this,""+dict.getTitle(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), noticefaqResult.class);

        intent.putExtra("title",dict.getTitle());
        intent.putExtra("contents",dict.getContents());

        startActivity(intent);
    }
}
