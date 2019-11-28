package com.example.finalp;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class nav extends AppCompatActivity {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private AppBarConfiguration mAppBarConfiguration;
    private Button verified_button;
    private TextView textview_nickname;
    private TextView textview_email;
    private ImageButton photo_button;
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    private String pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View header = navigationView.getHeaderView(0);
        textview_nickname = (TextView) header.findViewById(R.id.text_nav_nickname) ;
        textview_email = (TextView) header.findViewById(R.id.text_nav_email) ;

        FirebaseUser user=Auth.getCurrentUser();



        textview_email.setText(user.getEmail().toString());



        if(Auth.getCurrentUser()!=null){//UserInfo에 등록되어있는 닉네임을 가져오기 위해서
            mStore.collection("user").document(Auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult()!=null){
                                 Log.d("닉네임","닉:"+task.getResult().getData().get(FirebaseID.nickname));
                                //pp=(String)task.getResult().getData().get(FirebaseID.nickname);//이부분이 안되네
                                textview_nickname.setText((String)task.getResult().getData().get(FirebaseID.nickname));
                                //파이어베이스에 등록된 닉네임을 불러옴
                            }
                        }
                    });
        }

        //사진 삽입 버튼 구현
        photo_button = (ImageButton)findViewById(R.id.btn_photo);
        photo_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void OnClick(View view){
                Filechooser();
            }
        });
        
        //이메일 인증 버튼 구현
        verified_button = (Button) header.findViewById(R.id.btn_verified) ;
        if(user.isEmailVerified()){
            verified_button.setEnabled(false);
            verified_button.setBackgroundColor(Color.argb(0,0,0,0));
            verified_button.setText("세종대학교 재학중");
        }
        else {
            verified_button.setOnClickListener(new Button.OnClickListener() {
                final FirebaseUser user = Auth.getCurrentUser();

                @Override
                public void onClick(View view) {
                    if (user != null) {

                        if (user.isEmailVerified()) {

                        } else {
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "이메일을 전송했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
        }







        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notice, R.id.nav_license,
                R.id.nav_settings, R.id.nav_myinfo, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
