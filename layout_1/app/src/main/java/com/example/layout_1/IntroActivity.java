package com.example.layout_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        ImageButton open = (ImageButton) findViewById(R.id.imageButton);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClass dialog = new CustomDialogClass(IntroActivity.this);
                dialog.show();
            }
        });

    }
}
