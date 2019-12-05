package com.example.finalp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class noticefaqResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqnotice_result);

        String title="";
        String contents = "";

        Bundle extras = getIntent().getExtras();

        title = extras.getString("title");
        contents = extras.getString("contents");

        TextView textView = (TextView) findViewById(R.id.faqresult_title);
        TextView textView2 = (TextView) findViewById(R.id.faqresult_context);

        textView.setText(title);
        textView2.setText(contents);

    }
}
