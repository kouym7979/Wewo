package com.example.project_wewo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MemberRegisterActivity extends AppCompatActivity {
    EditText id_Text,pw_Text,pwck_Text;
    String sId, sPw, sPw_chk;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //activity_register.xml 파일에서 회원가입한 값을 가져옴
        id_Text = (EditText)findViewById(R.id.et_Id);
        pw_Text = (EditText)findViewById(R.id.et_Password);
        pwck_Text = (EditText)findViewById(R.id.et_Password_chk);

        Button registerButton = (Button) findViewById(R.id.memberrequest);
    }

    public void bt_Join(View view)
    {
        /*버튼을 눌렀을 때 동작하는 소스*/
        sId = id_Text.getText().toString();
        sPw = pw_Text.getText().toString();
        sPw_chk =  pwck_Text.getText().toString();

        if(sPw.equals(sPw_chk))
        {
            //패스워드 확인이 일치
        }

        else
        {
            //패스워드 확인이 불일치
        }
    }

}
