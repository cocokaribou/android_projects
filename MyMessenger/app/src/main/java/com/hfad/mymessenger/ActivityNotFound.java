package com.hfad.mymessenger;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityNotFound extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_found);


        Intent intent = getIntent();
        String inputMessage = intent.getStringExtra("input");
        TextView input_view = (TextView) findViewById(R.id.input_text2);
        input_view.setText(inputMessage);

        String errorMessage = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView error_view = (TextView) findViewById(R.id.error_message2);
        error_view.setText(errorMessage);

    }
}
