package com.example.finalp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Post_Comment extends AppCompatActivity {

    private TextView com_title;
    private TextView com_text;
    private TextView com_nick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__comment);

        com_nick=(TextView)findViewById(R.id.Comment_nickname);
        com_title=(TextView)findViewById(R.id.Comment_title);
        com_text=(TextView)findViewById(R.id.Comment_text);

        Intent intent=getIntent();//데이터 전달받기
       // String com_te=intent.getStringExtra("content");//PostAdapter에서 받아온 본문내용 저장
        //String com_ti=intent.getExtras().getString("title");//받아온 제목 저장
        //String com_ni=intent.getExtras().getString("nickname");
       // Log.d("확인","본문"+com_te.toString());
        int com_pos=intent.getExtras().getInt("position");//

        com_nick.setText(intent.getStringExtra("nickname"));
        com_text.setText(intent.getStringExtra("content"));
        com_title.setText(intent.getStringExtra("title"));

       // Log.d("확인 nick"+intent.getExtras().getString("nickname"));

    }
}
