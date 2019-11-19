package com.example.finalp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.post1).setOnClickListener(this);//외국인 친구 찾기 게시판


    }

    @Override
    public void onClick(View v) {//이렇게 연결한이유는 switch문으로 바꿔서 get.id값마다 다른 게시판으로 이동하는게 코드가 편함
        switch (v.getId())
        {
            case R.id.post1:
                Toast.makeText(HomeActivity.this,"외국인 친구찾기 게시판",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,NoticeBoardActivity.class));//일단 하나만 연동
        }
    }
}
