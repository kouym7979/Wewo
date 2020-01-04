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
        dataList.add(new faqCardItem("글은 어떻게 쓰나요?", "메인화면에서 원하는 게시판에 들어가셔서 오른쪽 하단에 있는 핑크색 펜 버튼을 누르신 후 글을 작성하신 후에 SAVE버튼을 누르시면 글 업로드가 완료됩니다! 감사합니다."));
        dataList.add(new faqCardItem("비밀번호를 잃어버리면 어떻게 하나요?","로그인 화면에서 findPW를 누르신 후에 본인이 가입 시 기재하였던 이메일 주소를 기재하시고 찾기를 누르시면 됩니다!"));
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
