package com.example.teamplay_p.dto.meeting;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MeetingResponse {

    private String meetingUuid;
    @SerializedName("subjectName")
    private String subjectName;

    private int subjectNum;

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

    public MeetingResponse(String subjectName,int subjectNum, String teamType, String username, String userMajor, String studentNumber, int desiredCount, String title, String description, String meetingUuid,Date date ){
        this.subjectName = subjectName;
        this.subjectNum = subjectNum;
        this.teamType = teamType;
        this.username = username;
        this.userMajor = userMajor;
        this.studentNumber = studentNumber;
        this.desiredCount = desiredCount;
        this.title = title;
        this.description = description;
        this.meetingUuid = meetingUuid;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = date;


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

    public Date getDate() {
        return date;
    }

    public int getsubjectNum() {
        return subjectNum;
    }

    // 변경된 부분: dateString 반환하는 메서드 추가

}
