package com.example.teamplay_p.dto.subject;

import com.example.teamplay_p.dto.major.Major;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class subject {
    @SerializedName("subjectUuid")
    private UUID subjectUuid;
    @SerializedName("subjectName")
    private String subjectName;
    @SerializedName("majorUuid")
    private Major majorUuid;

    public subject(UUID subjectUuid, String subjectName, Major majorUuid){
        this.subjectUuid = subjectUuid;
        this.subjectName = subjectName;
        this.majorUuid = majorUuid;
    }

    // 스피너에 표시될 문자열
    @Override
    public String toString(){
        return subjectName;
    }

    public UUID getSubjectUuid() {
        return subjectUuid;
    }

    public void setSubjectUuid(UUID subjectUuid) {
        this.subjectUuid = subjectUuid;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Major getMajorUuid() {
        return majorUuid;
    }

    public void setMajorUuid(Major majorUuid) {
        this.majorUuid = majorUuid;
    }
}
