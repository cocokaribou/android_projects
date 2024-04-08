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

        viewPager = findViewById(R.id.viewPager);
        view = findViewById(R.id.click_view);
        myPagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(1); //시작점

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("tag", "scrolled");
            }

            @Override
            public void onPageSelected(int position) {
                if (position == myPagerAdapter.getCount() - 1) {
                    view.setVisibility(View.VISIBLE);
                    view.setOnClickListener(view -> {
                        CustomDialog dialog = new CustomDialog(MainActivity.this);
                        dialog.start();
                    });
                } else {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}