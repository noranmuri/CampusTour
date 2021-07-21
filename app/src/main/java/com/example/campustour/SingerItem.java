package com.example.campustour;

public class SingerItem {
    String name;
    String mobile;
    int resld;

    public SingerItem(String name, String mobile, int resld) {
        this.name = name;
        this.mobile = mobile;
        this.resld = resld;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile){
        this.mobile=mobile;
    }
    public int getResld(){
        return resld;
    }
    @Override
    public String toString() {
        return "SingerItem{"+"name='"+name+'\''+", mobile='"+mobile+'\''+'}';
    }
}

