package com.example.teamplay_p.dto.user;

import com.example.teamplay_p.dto.major.Major;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {

    @SerializedName("userUuid")
    private UUID userUuid;
    @SerializedName("username")
    private String username;
    @SerializedName("userId")
    private String userId;
    @SerializedName("password")
    private String password;
    @SerializedName("studentNumber")
    private String studentNumber;
    @SerializedName("mainMajor")
    private Major mainMajor;
    @SerializedName("subMajor1")
    private Major subMajor1;
    @SerializedName("subMajor2")
    private Major subMajor2;
    @SerializedName("role")
    private UserRole role;
}
