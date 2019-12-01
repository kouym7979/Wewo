package com.example.finalp.ui.logout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.finalp.LoginActivity;
import com.example.finalp.NoticeBoardActivity;
import com.example.finalp.R;
import com.example.finalp.nav;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends DialogFragment{
    private Context context;
    public LogoutFragment() {}
    public static LogoutFragment getInstance(){
        LogoutFragment e = new LogoutFragment();
        return e;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logout, container, false);


        context = container.getContext();
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.logout_yes : //로그아웃하면 다시 로그인 화면으로 이동
                        Toast.makeText(context,"로그아웃 할게요",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        //getActivity().onBackPressed();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Intent i = getActivity().getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);


                        break ;
                    case R.id.logout_no : //로그아웃안하면 직전의 화면으로 이동해야하지만 아직 그것을 구현하지 못함
                        Toast.makeText(context,"로그아웃 안할래요",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), nav.class));
                        break ;

                }
            }
        } ;
        Button logout_yes_button = (Button) v.findViewById(R.id.logout_yes) ;
        logout_yes_button.setOnClickListener(onClickListener) ;
        Button logout_no_button = (Button) v.findViewById(R.id.logout_no) ;
        logout_no_button.setOnClickListener(onClickListener) ;


        return v;
    }



}