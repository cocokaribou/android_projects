package com.example.recycler_view3;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private Drawable iconDrawable;
    private String titleStr;
    private String descStr;

    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }

    public void setTitle(String title){
        titleStr = title;
    }

    public void setDesc(String desc){
        descStr = desc;
    }

    public Drawable getIconDrawable(){
        return this.iconDrawable;
    }

    public String getTitleStr(){
        return this.titleStr;
    }

    public String getDescStr(){
        return this.descStr;
    }
}
