package com.example.constraint_layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RelativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
    }

    public void goClose(View view){
        Intent intent = new Intent(getApplicationContext(), NoActivity.class);
        startActivity(intent);
    }

    public void goLogin(View view){
        Intent intent = new Intent(getApplicationContext(), NoActivity.class);
        startActivity(intent);
    }

    public void goPush(View view){
        Intent intent = new Intent(getApplicationContext(), NoActivity.class);
        startActivity(intent);
    }
}
