package com.example.teamplay_p.dto.major;

import com.google.gson.annotations.SerializedName;

public class MajorReqDto {
    @SerializedName("majorName")
    private String majorName;

    public MajorReqDto(String majorName){
        this.majorName = majorName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
