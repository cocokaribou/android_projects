package com.example.recycler_view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView; //모듈 레벨 build.gradle에서 먼저 추가

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//RecyclerView.Adapter를 상속받을 때 ViewHolder Type을 지정해야한다
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView korean;
        protected TextView english;

        public CustomViewHolder(View view) {
            super(view); //ViewHolder 생성자를 부르는 거겠지..
            this.id = (TextView) view.findViewById(R.id.id_listitem);
            this.english = (TextView) view.findViewById(R.id.english_listitem);
            this.korean = (TextView) view.findViewById(R.id.korean_listitem);
        }
    }

    private ArrayList<Dictionary> mList;

    //CustomAdapter 생성자
    //CustomAdapter 객체를 생성할 때 Dictionary 클래스 객체가 담긴 어레이리스트를 CustomAdapter 필드에 넣어주는군_
    public CustomAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        Log.d("tag1", "create");
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {

        //setTextSize
        viewHolder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewHolder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewHolder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        //setGravity
        viewHolder.id.setGravity(Gravity.CENTER);
        viewHolder.english.setGravity(Gravity.CENTER);
        viewHolder.korean.setGravity(Gravity.CENTER);

        //setText
        viewHolder.id.setText(mList.get(position).getId());
        viewHolder.english.setText(mList.get(position).getEnglish());
        viewHolder.korean.setText(mList.get(position).getKorean());

        Log.d("tag2", "bind");
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
