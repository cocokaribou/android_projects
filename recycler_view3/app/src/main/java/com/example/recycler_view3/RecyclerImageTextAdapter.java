package com.example.recycler_view3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


//커스텀 뷰홀더 타입의 커스텀 어댑터(RecyclerView.Adapter< >를 상속한)
public class RecyclerImageTextAdapter extends RecyclerView.Adapter<RecyclerImageTextAdapter.ViewHolder> {

    private ArrayList<RecyclerItem> mData = null;

    //adapter 생성자
    //뷰홀더에 담길 data class를 인자로 받아서 초기화
    RecyclerImageTextAdapter(ArrayList<RecyclerItem> list) {
        mData = list;
    }

    //adpater에서 오버라이드해야되는 함수
    //1. onCreateViewHolder

    @Override
    public RecyclerImageTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //viewType은 안 쓰네?
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recylcer_item, parent, false);
        RecyclerImageTextAdapter.ViewHolder vh = new RecyclerImageTextAdapter.ViewHolder(view);

        return vh;
    }
    //2. onBindViewHolder

    @Override
    public void onBindViewHolder(RecyclerImageTextAdapter.ViewHolder holder, int position) {
        RecyclerItem item = mData.get(position);

        holder.icon.setImageDrawable(item.getIconDrawable());
        holder.title.setText(item.getTitleStr());
        holder.desc.setText(item.getDescStr());
    }
    //3. getItemCount

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //adapter 클래스 안에 viewholder 클래스 구현
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;

        ViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
