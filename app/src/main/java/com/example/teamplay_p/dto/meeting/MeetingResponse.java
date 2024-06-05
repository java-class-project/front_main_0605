package com.example.teamplay_p.dto.meeting;

import com.google.gson.annotations.SerializedName;

public class MeetingResponse {
    @SerializedName("subjectName")
    private String subjectName;

    @SerializedName("desiredCount")
    private int desiredCount;

    @SerializedName("teamType")
    private String teamType;

    @SerializedName("username")
    private String username;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public MeetingResponse(String subjectName, String teamType, String username, int desiredCount, String title, String description){
        this.subjectName = subjectName;
        this.teamType = teamType;
        this.username = username;
        this.desiredCount = desiredCount;
        this.title = title;
        this.description = description;


    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTeamType() {
        return  teamType;
    }

    public String getUsername() {
        return username;
    }

    public int getDesiredCount() {
        return desiredCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
