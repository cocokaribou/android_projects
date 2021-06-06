package com.example.recycler_view4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recycler_view4.adapter.CustomAdapter;
import com.example.recycler_view4.data.Goods;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Goods> tempData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempData = new ArrayList<Goods>();
        tempData.add(new Goods("shirley", R.drawable.shirley, "American Actress"));
        tempData.add(new Goods("barbra", R.drawable.barbra, "American Actress"));
        tempData.add(new Goods("robert", R.drawable.robert,  "American Actor"));
        tempData.add(new Goods("humphrey", R.drawable.humphrey, "American Actor"));


        RecyclerView.Adapter myAdapter = new CustomAdapter(tempData);
        RecyclerView.LayoutManager linearManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(linearManager);

        Button refresh = (Button)findViewById(R.id.btn_refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.Adapter myAdapter = new CustomAdapter(tempData);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

}