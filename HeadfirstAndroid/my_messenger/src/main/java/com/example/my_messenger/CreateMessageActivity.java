package com.example.my_messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    public void onSendMessage(View view) {
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString();

//        명시적으로 ReceiveMessageActivity를 요청하는 인텐트
//        Intent intent = new Intent(this, ReceiveMessageActivity.class);
//        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, messageText);
//        startActivity(intent);

//        암시적으로 intent 생성, 안드로이드가 제공하는 표준 액션
//        createChooser()
//        인텐트를 수신할 수 없는 상황도 처리 (근데 디바이스마다 다이얼로그가 좀 다르게 생겼나?)
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);

//        달력
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType("vnd.android.cursor.dir/event");
//        intent.putExtra(Intent.EXTRA_EMAIL, messageText);


//        인텐트를 수신할 수 있는 앱이 없는 상황 처리
//        혹은 인텐트를 처리할 수 있는 특정 앱을 다운로드하도록 링크 제공
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType("vnd.android.cursor.dir/event"); //타입 지정해주면 외부앱으로 잘 이동
//
//        try {
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            intent = new Intent(this, ActivityNotFound.class);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_TEXT, e.toString());
//            intent.putExtra("input", messageText);
//            startActivity(intent);
//        }

//        developer.android.com에서 공통인텐트 항목 참고

    }

}