package com.example.finalp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();//핸들러 선언
        hd.postDelayed(new splashHandler(), 1500);//1.5초뒤에 핸들러 작동
    }
    private class splashHandler implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            SplashActivity.this.finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);//액비티미 이동간의 애니메이션 효과적용
        }
    }
}
