package com.hfad.swipe_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapter extends PagerAdapter {

    private static int ITEM_NUM = 3;
    private Context mContext = null;
    private View[] ViewArr = new View[ITEM_NUM];
    //ArrayList로 바꾸는게 나을까
    public MyPagerAdapter(){
    }

    //Context를 전달받아 mContext에 저장하는 생성자 추가.
    public MyPagerAdapter(Context context){
        mContext = context;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View constraint_view = null;
        View dialog_view = null;
        View intro_view = null;

        if(mContext!=null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            constraint_view = inflater.inflate(R.layout.constraint_layout, container, false);
            dialog_view = inflater.inflate(R.layout.dialog_intro, container, false);
            intro_view = inflater.inflate(R.layout.intro, container, false);
            ViewArr[0] = intro_view;
            ViewArr[1] = constraint_view;
            ViewArr[2] = dialog_view;

        }

        //뷰페이저에 추가.
        container.addView(ViewArr[position]);

        return ViewArr[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    //전체 페이지
    @Override
    public int getCount() {
        return ITEM_NUM;
    }

    //페이지뷰가 키 객체와 연관되는지 확인.
    //어떤 정보를 키 객체로 사용할 것인가,
    //즉 instantiateItem()에서 리턴하는 객체 종류에 따라
    //메서드의 구현코드 결정됨
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(View)object);
    }
}
