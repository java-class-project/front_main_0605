package com.example.teamplay_p.front;

import java.util.ArrayList;

public class UserInfoManager {

    private static UserInfoManager instance;
    private ArrayList<Integer> userInfo;

    private UserInfoManager() {
        userInfo = new ArrayList<>();
    }

    public static synchronized UserInfoManager getInstance() {
        if (instance == null) {
            instance = new UserInfoManager();
        }
        return instance;
    }

    public ArrayList<Integer> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<Integer> userInfo) {
        this.userInfo = userInfo;
    }


}
