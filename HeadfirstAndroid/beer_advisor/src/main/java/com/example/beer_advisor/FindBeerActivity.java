package com.example.beer_advisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class FindBeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);

        TextView text = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        Spinner spinner = findViewById(R.id.spinner);

        button.setOnClickListener(v -> { //lambda
            String color = spinner.getSelectedItem().toString();
            Log.e("color", color);
            ArrayList<String> brandList = BeerExpert.INSTANCE.getBrands(color);
            String brand = "";
            StringBuilder formatter = new StringBuilder();
            for(String b:brandList){
                brand = formatter.append(b).append("\n").toString();
            }
            text.setText(brand);
        });
    }

}