package com.example.recycler_view4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler_view4.R;
import com.example.recycler_view4.data.Goods;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Goods> goodsList;
    private int RAND = new Random().nextInt(5);

    public CustomAdapter(ArrayList<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_surprise, parent, false);
                return new surpriseHolder(view);
            }
            default: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list, parent, false);
                return new listHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof listHolder) {
            int index = position;
            if (RAND == 0 || position > RAND) {
                index--;
            }
            ((listHolder) holder).name.setText(goodsList.get(index).getName());
            ((listHolder) holder).desc.setText(goodsList.get(index).getDesc());
            ((listHolder) holder).img.setImageResource(goodsList.get(index).getImg());
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

    class listHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        TextView desc;

        public listHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_itemNm);
            img = itemView.findViewById(R.id.img_itemImg);
            desc = itemView.findViewById(R.id.txt_itemDesc);
        }
    }

    class surpriseHolder extends RecyclerView.ViewHolder {
        public surpriseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
