package com.example.teamplay_p.dto.meeting;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class MeetingResponse {

    private String meetingUuid;
    @SerializedName("subjectName")
    private String subjectName;

    @SerializedName("desiredCount")
    private int desiredCount;

    @SerializedName("teamType")
    private String teamType;

    @SerializedName("username")
    private String username;

    private String UserId;

    private String studentNumber;

    private String userMajor;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;


    private Date date;

    public MeetingResponse(String subjectName, String teamType, String username, String userMajor, String studentNumber, int desiredCount, String title, String description, String meetingUuid){
        this.subjectName = subjectName;
        this.teamType = teamType;
        this.username = username;
        this.userMajor = userMajor;
        this.studentNumber = studentNumber;
        this.desiredCount = desiredCount;
        this.title = title;
        this.description = description;
        this.meetingUuid = meetingUuid;


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

    public String getuserMajor() {
        return userMajor;
    }



    public String getuserstudentNumber() {
        return studentNumber;
    }

    public String getMeetingUuid() {
        return meetingUuid;
    }

    public String getUserId() {
        return UserId;
    }
}
