package com.example.blindapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import com.example.blindapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

//      setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        // 화면 이동
        // 이동하는 INTENT를 미리 만들어둔다
        Intent i = new Intent(this, RedirectActivity.class);
        binding.ivArrow.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                // 클릭시 이동하는 화면 띄우는 로직
                startActivity(i);
            }
        });

        // 드롭다운 버튼 선택시 list 띄우기
        // fragment를 붙인다

        binding.tvWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frag_container, new SelectFragment(), "selectfragment")
                        .commit();

                binding.background.setVisibility(View.VISIBLE);
                binding.background.setAlpha(0.5f);
            }
        });

    }
}