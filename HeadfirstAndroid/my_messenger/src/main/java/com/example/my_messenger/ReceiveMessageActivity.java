package com.example.my_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiveMessageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("⚠️", "1 created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        Intent intent = getIntent(); //액티비티를 시작시킨 인텐트 반환. 인텐트에 포함된 추가정보 추출 가능, 즉 추출하려고 굳이굳이 get
        String messageText = intent.getStringExtra(EXTRA_MESSAGE);
        TextView messageView = (TextView) findViewById(R.id.message);
        messageView.setText(messageText);

    }

}
