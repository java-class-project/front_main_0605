package com.example.teamplay_p.dto.major;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Major {
    @SerializedName("majorName")
    private String majorName;
    @SerializedName("majorUuid")
    private UUID majorUuid;

    // 생성자
    public Major(String majorName, UUID majorUuid){
        this.majorName = majorName;
        this.majorUuid = majorUuid;
    }

    // 스피너에 표시될 문자열
    @Override
    public String toString(){
        return majorName;
    }

    public String getMajorName() {

        return majorName;
    }
    public void setMajorName(String majorName) {

        this.majorName = majorName;
    }

    public UUID getMajorUuid() {
        return majorUuid;
    }

    public void setMajorUuid(UUID majorUuid) {

        this.majorUuid = majorUuid;
    }

}
