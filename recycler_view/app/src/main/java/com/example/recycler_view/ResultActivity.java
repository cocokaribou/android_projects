package com.example.recycler_view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String id = "";
        String korean = "";
        String english = "";


        // intent의 함수들
        // getStringExtra하면 string을 뱉고
        // getExtra하면 bundle을 뱉는구만
        Bundle extras = getIntent().getExtras();

        // bundle에서 뽑아쓸수가 있군
        // 근데 이게 어디서 나온 bundle이여?
        // main activity에 putExtra로 추
        id = extras.getString("id");
        korean = extras.getString("korean");
        english = extras.getString("english");

        //activity_result 레이아웃을 불러온다
        TextView textView = (TextView) findViewById(R.id.textView_result);

        String str = id+'\n'+english+'\n'+korean;
        textView.setText(str);

    }
}
