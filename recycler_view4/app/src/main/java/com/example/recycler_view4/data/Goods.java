package com.example.recycler_view4.data;

public class Goods {
    private String name;
    private int img;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public Goods(String name, int img, String desc) {
        this.name = name;
        this.img = img;
        this.desc = desc;
    }
}
