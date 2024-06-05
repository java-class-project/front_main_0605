package com.example.teamplay_p.dto.meeting;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;


public class CreateMeetingRequest {

    @SerializedName("teamType")   // "TeamProject", "Study", "Project"
    private String teamType;
    @SerializedName("majorUuid")
    private UUID majorUuid;
    @SerializedName("subjectUuid")
    private UUID subjectUuid;
    @SerializedName("classNum")
    private int classNum;
    @SerializedName("desiredCount")
    private int desiredCount;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;


    public CreateMeetingRequest(String teamType, UUID majorUuid, UUID subjectUuid, int classNum, int desiredCount, String title, String description){
        this.teamType = teamType;
        this.majorUuid = majorUuid;
        this.subjectUuid = subjectUuid;
        this.classNum = classNum;
        this.desiredCount = desiredCount;
        this.title = title;
        this.description = description;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public UUID getMajorUuid() {
        return majorUuid;
    }

    public void setMajorUuid(UUID majorUuid) {
        this.majorUuid = majorUuid;
    }

    public UUID getSubjectUuid() {
        return subjectUuid;
    }

    public void setSubjectUuid(UUID subjectUuid) {
        this.subjectUuid = subjectUuid;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public int getDesiredCount() {
        return desiredCount;
    }

    public void setDesiredCount(int desiredCount) {
        this.desiredCount = desiredCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

