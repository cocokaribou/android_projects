package com.hfad.swipe_view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private View view;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("로그 찍히긴 하지?", "당근이지머..");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        view = (View) findViewById(R.id.click_view);
        myPagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(myPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("tag", "scrolled");
            }

            @Override
            public void onPageSelected(int position) {
                if (position == myPagerAdapter.getCount() - 1) {
                    view.setVisibility(View.VISIBLE);
                    Log.e("youngin", "⚠️");
                } else {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog dialog = new CustomDialog(MainActivity.this);
                dialog.start();
            }
        });

    }
}