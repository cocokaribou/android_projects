package com.example.blindapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blindapp.databinding.ActivityRedirectBinding;

public class RedirectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRedirectBinding binding = ActivityRedirectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
