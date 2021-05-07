package com.example.recycler_view2;

public class Dictionary {
    private String id;
    private String english;
    private String korean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public Dictionary(String id, String english, String korean) {
        this.id = id;
        this.english = english;
        this.korean = korean;
    }

}
