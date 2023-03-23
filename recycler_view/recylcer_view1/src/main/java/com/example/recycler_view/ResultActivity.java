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

        Bundle extras = getIntent().getExtras();

        id = extras.getString("id");
        korean = extras.getString("korean");
        english = extras.getString("english");

        TextView textView = (TextView) findViewById(R.id.textView_result);

        String str = id+'\n'+english+'\n'+korean;
        textView.setText(str);

    }
}
