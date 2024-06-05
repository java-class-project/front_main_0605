package com.example.teamplay_p.front;

import android.app.Application;

import java.util.ArrayList;

public class MyApp extends Application {
    private ArrayList<ArrayList<Integer>> userInfo = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<ArrayList<Integer>>  userInfo) {
        this.userInfo = userInfo;
    }




}
