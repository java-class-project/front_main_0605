package com.example.teamplay_p.dto.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UpdateRequest {
    @SerializedName("userUuid")
    @Expose
    private transient UUID uuid;
    @SerializedName("mainMajorName")
    @Expose
    public String mainMajor;
    @SerializedName("subMajor1Name")
    @Expose
    public String subMajor1;
    @SerializedName("subMajor2Name")
    @Expose
    public String subMajor2;

    //getter, setter 필요?
}
