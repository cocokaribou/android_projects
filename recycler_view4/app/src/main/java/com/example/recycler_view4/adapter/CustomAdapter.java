package com.example.recycler_view4.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler_view4.R;
import com.example.recycler_view4.data.Goods;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Goods> goodsList;
    private int RAND = new Random().nextInt(5);

    public CustomAdapter(ArrayList<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    private Context context;

    //item의 클릭상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    //직전에 클릭됐던 item의 position
    private int prePosition = -1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case 1: {
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.item_surprise, parent, false);
                return new surpriseHolder(view);
            }
            default: {
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.item_list, parent, false);
                return new listHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof listHolder) {
            ((listHolder) holder).onBind(goodsList, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //default
        if (position == RAND) {
            viewType = 1;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return goodsList.size() + 1;
    }

    class listHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView img;
        TextView desc;

        private ArrayList<Goods> list;
        private int position;

        public listHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_itemNm);
            img = itemView.findViewById(R.id.img_itemImg);
            desc = itemView.findViewById(R.id.txt_itemDesc);
        }

        void onBind(ArrayList<Goods> list, int position) {
            this.list = list;
            this.position = position;
            int index = position;
            if (RAND == 0 || position > RAND) {
                index--;
            }
            name.setText(goodsList.get(index).getName());
            desc.setText(goodsList.get(index).getDesc());
            img.setImageResource(goodsList.get(index).getImg());

            changeVisibility(selectedItems.get(position));

            name.setOnClickListener(this);
            desc.setOnClickListener(this);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_itemNm:
                case R.id.txt_itemDesc:
                case R.id.img_itemImg: {
                    if (selectedItems.get(position)) {
                        selectedItems.delete(position);
                    } else {
                        selectedItems.delete(prePosition);
                        selectedItems.put(position, true);
                    }

                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    prePosition = position;
                    break;
                }
                default: {

                }
            }
        }
    }

    class surpriseHolder extends RecyclerView.ViewHolder {
        public surpriseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private void changeVisibility(final boolean isExpanded){
        int dpValue = 150;
        float d = context.getResources().getDisplayMetrics().density;
        int height = (int)(dpValue * d);


    }
}
