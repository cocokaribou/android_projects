package com.example.recycler_view3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    RecyclerImageTextAdapter mAdapter = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate가 맨 첨에 나와야하나본데
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler1);

        mAdapter = new RecyclerImageTextAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        //layoutmanager 안 달아주면 뷰홀더 안 보인다
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //getDrawable 최소 지원 sdk가 21인데 지금 플젝은 kitkat 19여서 안되던 거였음
        Drawable icon = getDrawable(R.drawable.ic_account_circle_black);
        Drawable icon_trans = getDrawable(R.drawable.ic_account_transparent);

        addItem(icon, "Box", "Account Box");
        addItem(icon_trans, "Circle", "Account Circle");
        addItem(icon, "Ind", "Account Ind");

        mAdapter.notifyDataSetChanged();
    }

    public void addItem(Drawable icon, String title, String desc){
        RecyclerItem item = new RecyclerItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        this.mList.add(item);
    }
}