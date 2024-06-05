package com.example.teamplay_p.dto.auth;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("userId")
    private String userId;
    @SerializedName("password")
    private String password;

    // 생성자
    public LoginRequest(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    // Getter and Setter methods
    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}