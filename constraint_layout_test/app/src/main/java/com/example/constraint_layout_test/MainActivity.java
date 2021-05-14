package com.example.constraint_layout_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.constraint_layout_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button relative_btn = (Button)findViewById(R.id.btn_relative);
        Button constraint_btn = (Button)findViewById(R.id.btn_constraint);


    }

    public void toRelative(View view){
        Intent intent = new Intent(getApplicationContext(), RelativeActivity.class);
        startActivity(intent);
    }

    public void toConstraint(View view){
        Intent intent = new Intent(getApplicationContext(), ConstraintActivity.class);
        startActivity(intent);
    }

}